package com.example.loginbiometric



import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginbiometric.ui.theme.LoginBiometricTheme

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginBiometricTheme {
                // A surface container using the 'background' color from the theme
            Router()
            }
        }
    }
}

@Composable
fun Router() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { Home(navController) }
        composable("logged") { LoggedIn() }
        /*...*/
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController) {

    var name by remember {
        mutableStateOf("")
    }
//
    val context = LocalContext.current
    val activity = LocalContext.current as FragmentActivity
    val executor = ContextCompat.getMainExecutor(activity)

    var promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("title")
        .setSubtitle("subtitle")
        .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
        .build()

    val biometricPrompt = BiometricPrompt(activity, executor,
        object: BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(
                    context,
                    "error",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(context, "succeeded!", Toast.LENGTH_LONG).show()
                navController.navigate("live")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(
                    context, "failed", Toast.LENGTH_LONG
                ).show()
            }
        }
    )


    Column {
        Text(text = "Bienvenido")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Ingrese su nombre")
        TextField(value = name, onValueChange = {
            name = it
        })
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            if (name.isNullOrBlank()){
                Toast.makeText(context, "Ingrese un nombre", Toast.LENGTH_SHORT)
            }else{

                navController.navigate("live")
            }
        }) {
            Text(text = "Continuar")
        }



        Button(onClick = {

            biometricPrompt.authenticate(promptInfo)
        }) {
            Text(text = "Autenticación Biometrica")
        }


    }
}

@Composable
fun LoggedIn() {

}