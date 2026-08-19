package com.example.nexuspay.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.nexuspay.feature.home.domain.HOME_USER_IDENTIFIER
import com.example.nexuspay.feature.transactions.domain.repository.IUserRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class NexusPayApp : Application(), Configuration.Provider {
	@Inject lateinit var workerFactory: HiltWorkerFactory
	@Inject lateinit var userRepository: IUserRepository

	private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

	override val workManagerConfiguration: Configuration
		get() = Configuration.Builder().setWorkerFactory(workerFactory).build()

	override fun onCreate() {
		super.onCreate()
		// TODO: remove after login is implemented.
		applicationScope.launch {
			userRepository.saveIdentifier(HOME_USER_IDENTIFIER)
		}
	}
}
