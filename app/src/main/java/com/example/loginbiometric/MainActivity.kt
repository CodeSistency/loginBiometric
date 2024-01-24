package com.example.loginbiometric



import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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

@Preview
@Composable
fun PreviewLogin(){
    val navController = rememberNavController()

    PreviewHome()
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
@Composable
fun PreviewHome() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        // First Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color.Blue, Color.Green)
                    ),
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
        ) {
            Text(
                text = "Welcome",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Second Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = Color.Gray.copy(alpha = 0.1f))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { /* Handle fingerprint icon click */ },
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .shadow(4.dp, CircleShape)
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_fingerprint),
                    contentDescription = "Fingerprint Icon",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(120.dp)

                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController) {


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
                navController.navigate("logged")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(
                    context, "failed", Toast.LENGTH_LONG
                ).show()
            }
        }
    )




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        // First Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color.Blue, Color.Green)
                    ),
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
        ) {
            Text(
                text = "Welcome",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Second Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = Color.Gray.copy(alpha = 0.1f))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { biometricPrompt.authenticate(promptInfo) },
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .background(Color.White)
//                    .shadow(4.dp, CircleShape)
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_fingerprint),
                    contentDescription = "Fingerprint Icon",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(120.dp)

                )
            }
        }
    }

}

@Composable
fun LoggedIn() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Logged In")
    }
}