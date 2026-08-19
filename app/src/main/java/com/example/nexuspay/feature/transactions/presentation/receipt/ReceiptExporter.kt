package com.example.nexuspay.feature.transactions.presentation.receipt

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.compose.ui.graphics.toArgb
import com.example.nexuspay.design.theme.DarkBackground
import com.example.nexuspay.design.theme.DarkOnSurface
import com.example.nexuspay.design.theme.DarkOnSurfaceVariant
import com.example.nexuspay.design.theme.DarkSurface
import com.example.nexuspay.design.theme.NexusPayAccent
import com.example.nexuspay.feature.home.presentation.MoneyFormatter
import com.example.nexuspay.core.data.models.Transaction.Money
import com.example.nexuspay.feature.transactions.domain.model.TransactionListItem
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ReceiptExporter @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    suspend fun createShareImage(transaction: TransactionListItem): Uri = withContext(Dispatchers.IO) {
        val receipt = ReceiptDetails(transaction)
        val bitmap = Bitmap.createBitmap(RECEIPT_WIDTH, RECEIPT_HEIGHT, Bitmap.Config.ARGB_8888)
        Canvas(bitmap).drawReceipt(receipt)

        val file = File(context.cacheDir, "receipt-${transaction.id}.png")
        FileOutputStream(file).use { output -> bitmap.compress(Bitmap.CompressFormat.PNG, 100, output) }
        FileProvider.getUriForFile(context, "${context.packageName}.receiptprovider", file)
    }

    suspend fun savePdf(transaction: TransactionListItem): Uri = withContext(Dispatchers.IO) {
        val receipt = ReceiptDetails(transaction)
        val displayName = "NexusPay-receipt-${transaction.id}.pdf"
        val uri = createPdfUri(displayName)

        context.contentResolver.openOutputStream(uri)?.use { output ->
            val document = PdfDocument()
            try {
                val page = document.startPage(PdfDocument.PageInfo.Builder(RECEIPT_WIDTH, RECEIPT_HEIGHT, 1).create())
                page.canvas.drawReceipt(receipt)
                document.finishPage(page)
                document.writeTo(output)
            } finally {
                document.close()
            }
        } ?: error("Unable to create receipt PDF.")
        uri
    }

    private fun createPdfUri(displayName: String): Uri {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, displayName)
                put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }
            return requireNotNull(
                context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values),
            ) { "Unable to create receipt PDF." }
        }

        val directory = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            ?: error("Documents directory is unavailable.")
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.receiptprovider",
            File(directory, displayName),
        )
    }

    private fun Canvas.drawReceipt(receipt: ReceiptDetails) {
        drawColor(DarkBackground.toArgb())
        val accentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = NexusPayAccent.toArgb()
        }
        val headingPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = DarkOnSurface.toArgb()
            textSize = 32f
            isFakeBoldText = true
        }
        val primaryPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = DarkOnSurface.toArgb()
            textSize = 28f
        }
        val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = DarkOnSurfaceVariant.toArgb()
            textSize = 22f
        }
        val amountPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = DarkOnSurface.toArgb()
            textSize = 58f
            isFakeBoldText = true
        }

        drawRoundRect(RectF(0f, 0f, RECEIPT_WIDTH.toFloat(), RECEIPT_HEIGHT.toFloat()), 0f, 0f, accentPaint)
        drawRoundRect(RectF(0f, 14f, RECEIPT_WIDTH.toFloat(), RECEIPT_HEIGHT.toFloat()), 44f, 44f, Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = DarkBackground.toArgb()
        })

        drawCircle(RECEIPT_WIDTH / 2f, 108f, 44f, accentPaint)
        val checkPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            strokeWidth = 8f
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }
        drawLine(516f, 108f, 534f, 126f, checkPaint)
        drawLine(534f, 126f, 568f, 88f, checkPaint)

        drawCenteredText(receipt.amount, 218f, amountPaint)
        drawCenteredText("Transfer Amount", 254f, labelPaint)
        drawCenteredText("Send Money", 302f, headingPaint)
        drawCenteredText(receipt.status, 338f, labelPaint)

        var y = 402f
        receipt.recipient?.let { recipient ->
            drawSection("TO", recipient, y, labelPaint, primaryPaint)
            y += 112f
            drawCircle(RECEIPT_WIDTH / 2f, y - 30f, 14f, accentPaint)
            y += 26f
        }

        drawRoundRect(
            RectF(48f, y, RECEIPT_WIDTH - 48f, y + receipt.detailsHeight),
            24f,
            24f,
            Paint(Paint.ANTI_ALIAS_FLAG).apply { color = DarkSurface.toArgb() },
        )
        var detailY = y + 46f
        receipt.details.forEach { (label, value) ->
            drawText(label.uppercase(), 80f, detailY, labelPaint)
            drawText(value, 80f, detailY + 34f, primaryPaint)
            detailY += 94f
        }

        drawCenteredText("NexusPay", RECEIPT_HEIGHT - 70f, headingPaint)
        drawCenteredText("Secure digital transfers", RECEIPT_HEIGHT - 34f, labelPaint)
    }

    private fun Canvas.drawSection(
        label: String,
        value: String,
        y: Float,
        labelPaint: Paint,
        primaryPaint: Paint,
    ) {
        drawText(label, 80f, y, labelPaint)
        drawText(value, 80f, y + 38f, primaryPaint)
    }

    private fun Canvas.drawCenteredText(text: String, baseline: Float, paint: Paint) {
        drawText(text, (RECEIPT_WIDTH - paint.measureText(text)) / 2f, baseline, paint)
    }

    private data class ReceiptDetails(val transaction: TransactionListItem) {
        val amount: String = MoneyFormatter.format(Money(transaction.amount, transaction.currency))
        val status: String = transaction.state.replaceFirstChar { it.uppercase() }
        val recipient: String? = transaction.description.recipientName()
        val details: List<Pair<String, String>> = buildList {
            add("Reference" to transaction.id)
            add("Date and time" to "${transaction.date} ${transaction.time}")
            add("Currency" to transaction.currency)
            transaction.description.takeIf { it.isNotBlank() && recipient == null }?.let {
                add("Note" to it)
            }
        }
        val detailsHeight: Float = details.size * 94f + 38f
    }
}

private fun String.recipientName(): String? = when {
    startsWith("Sent to ") -> removePrefix("Sent to ").takeIf { it.isNotBlank() }
    startsWith("Received from ") -> removePrefix("Received from ").takeIf { it.isNotBlank() }
    else -> null
}

private const val RECEIPT_WIDTH = 1080
private const val RECEIPT_HEIGHT = 1280