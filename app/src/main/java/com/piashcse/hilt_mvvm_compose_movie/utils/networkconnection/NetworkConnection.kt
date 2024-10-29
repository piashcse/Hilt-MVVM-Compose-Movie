package com.piashcse.hilt_mvvm_compose_movie.utils.networkconnection

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

/**
 * Extension property to retrieve the current connectivity state as [ConnectionState].
 *
 * This property accesses the system's [ConnectivityManager] and checks if the
 * device has an active internet connection.
 *
 * @return [ConnectionState.Available] if there is an active internet connection,
 * otherwise [ConnectionState.Unavailable].
 */
val Context.currentConnectivityState: ConnectionState
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getCurrentConnectivityState(connectivityManager)
    }

/**
 * Utility function to check the current connectivity state of the device.
 *
 * This function uses the [ConnectivityManager] to check if the currently active network
 * has internet capability.
 *
 * @param connectivityManager The [ConnectivityManager] instance used to check the network status.
 * @return [ConnectionState.Available] if there is an active network with internet capability,
 * otherwise [ConnectionState.Unavailable].
 */
private fun getCurrentConnectivityState(
    connectivityManager: ConnectivityManager
): ConnectionState {
    val network = connectivityManager.activeNetwork
    val isConnected = network?.let {
        connectivityManager.getNetworkCapabilities(it)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    } == true

    return if (isConnected) ConnectionState.Available else ConnectionState.Unavailable
}

/**
 * Observes network connectivity changes as a [Flow] of [ConnectionState].
 *
 * This function uses a [ConnectivityManager.NetworkCallback] to listen for changes in
 * network connectivity and emits updates via [callbackFlow].
 *
 * @return A [Flow] that emits the current [ConnectionState] whenever connectivity changes.
 */
fun Context.observeConnectivityAsFlow() = callbackFlow {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val callback = networkCallback { connectionState -> trySend(connectionState) }

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    connectivityManager.registerNetworkCallback(networkRequest, callback)

    // Set current state
    val currentState = getCurrentConnectivityState(connectivityManager)
    trySend(currentState)

    // Remove callback when flow collection stops
    awaitClose {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

/**
 * Creates a [ConnectivityManager.NetworkCallback] that listens for network availability changes.
 *
 * This callback notifies the provided [callback] function of the current [ConnectionState]
 * whenever the network is available or lost.
 *
 * @param callback A function that receives the current [ConnectionState].
 * @return A [ConnectivityManager.NetworkCallback] instance that can be registered with a [ConnectivityManager].
 */
fun networkCallback(callback: (ConnectionState) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback(ConnectionState.Available)
        }

        override fun onLost(network: Network) {
            callback(ConnectionState.Unavailable)
        }
    }
}

/**
 * A composable function that provides the current network connectivity state as a [State] object.
 *
 * This function uses [observeConnectivityAsFlow] to observe changes in connectivity
 * and updates the returned [State] value with the latest [ConnectionState].
 *
 * @return A [State] object that holds the latest [ConnectionState] (either Available or Unavailable).
 */
@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current

    // Creates a State<ConnectionState> with the current connectivity state as the initial value
    return produceState(initialValue = context.currentConnectivityState) {
        // Collects connectivity updates in a coroutine and updates the State value
        context.observeConnectivityAsFlow().collect {
            value = it
        }
    }
}