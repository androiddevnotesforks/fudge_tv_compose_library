# 🍮 Fudge: Jetpack Compose TV UI Kit 🎬

**Fudge** is a 🌟 Jetpack Compose library module designed to make the development of TV interfaces and applications smoother and sweeter. Tailored specifically for the unique needs of TV UIs, Fudge offers a collection of pre-built components and tools that help you create immersive and user-friendly experiences on large screens.

<p align="center">
  <img src="https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white" />
  <img src="https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" />
  <img src="https://img.shields.io/badge/Material%20UI-007FFF?style=for-the-badge&logo=mui&logoColor=white" />
</p>

## ⚠️ Disclaimer  

This project has been developed **exclusively for learning and experimentation purposes**. **Fudge** is an exploratory Jetpack Compose UI kit designed to gain insights into TV interface development, focus management, and state handling in Android applications.  


### Features 🎉

- **Pre-defined Components:** Fudge provides a range of ready-to-use components optimized for TV, including focusable buttons, lists, and custom navigation drawers, ensuring your app looks and performs beautifully on the big screen.
- **Focus Management:** Built with TV in mind, Fudge simplifies focus handling and navigation, making it easier to manage remote control interactions.
- **State Management:** Supports advanced screen state management by implementing patterns like MVI (Model-View-Intent) or MVVM (Model-View-ViewModel), delivering fluid and responsive UIs.
- **Customization:** Fudge components are highly customizable, allowing you to adapt them to match the unique style and branding of your TV application.
- **Jetpack Compose Compatibility:** Fully integrated with Jetpack Compose, the modern Android UI framework, ensuring a seamless and efficient development experience tailored for TV platforms.

## Model-View-Intent (MVI) Architecture 🏗️

Fudge encourages the use of the Model-View-Intent (MVI) architecture pattern for effective screen state management. In this pattern:

- **Model:** Represents the state of the UI. Fudge provides a `FudgeTvViewModel` class that extends from `ViewModel` and handles the UI state.
- **View:** Renders the UI based on the state provided by the ViewModel. Fudge components are seamlessly integrated with Compose to create a declarative UI.
- **Intent:** Represents user actions or events that trigger state changes. Fudge's components, like buttons and text fields, are designed to emit these intents efficiently.

This setup leverages Fudge's components and ViewModel to accelerate the development of robust features while adhering to best practices in architecture and UI design.

Here's an example of how you can use FudgeTv components in a screen following the MVI pattern:

```kotlin
@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    onGoToHome: () -> Unit,
    onGoToProfiles: () -> Unit,
    onGoToSignUp: () -> Unit,
    onBackPressed: () -> Unit,
) {
    FudgeTvScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { SignInUiState() },
        onSideEffect = {
            when(it) {
                SignInSideEffects.AuthenticationSuccessfully -> onGoToHome()
                SignInSideEffects.ProfileSelectionRequired -> onGoToProfiles()
                SignInSideEffects.CreateNewAccount -> onGoToSignUp()
            }
        }
    ) { uiState ->
        SignInScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}
```

Inside the SignInScreen function, there's a FudgeTvScreen composable. This is a custom screen component provided by the FudgeTV library. It's responsible for managing the UI state and handling side effects in a declarative way. Here's what each parameter does:

- **viewModel:** The **SignInViewModel** instance passed to the **FudgeTvScreen** component.
- **onBackPressed:** The lambda function passed to the **FudgeTvScreen** component, which is called when the user navigates back from the screen.
- **onInitialUiState:** A lambda function that provides the initial UI state for the screen. In this case, it returns a new instance of **SignInUiState.**
- **onSideEffect:** A lambda function that handles side effects triggered by the ViewModel. When a side effect occurs (such as successful authentication or profile selection required), this function is called, and appropriate actions (navigating to home or profile selection) are performed.

Finally, inside the FudgeTvScreen composable, there's another composable function called **SignInScreenContent.** This is the actual content of the sign-in screen, where UI elements like text fields, buttons, etc., are defined. It takes the current UI state (uiState) as a parameter and callback functions for handling user interactions:

- **uiState:** The current state of the sign-in screen, which is provided by the FudgeTvScreen component.
- **onEmailChanged:** A callback function for handling changes in the email input field.
- **onPasswordChanged:** A callback function for handling changes in the password input field.
- **onSignIn:** A callback function for handling the sign-in action.

Overall, this implementation follows a declarative approach using Compose and Fudge components, making it easier to manage the UI state and handle user interactions in a clear and concise manner.

```kotlin
@Composable
internal fun SignInScreenContent(
    uiState: SignInUiState,
    actionListener: SignInScreenActionListener
) {
    FudgeTvScreenContent(
        error = uiState.errorMessage,
        onErrorAccepted = actionListener::onErrorMessageCleared
    ) {
        SignInDialog(uiState = uiState)
        SignInVideoBackground()
        SignInLogo()
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.9f),
            verticalArrangement = Arrangement.Center,
        ) {
            SignInMainContent(
                uiState = uiState,
                onEmailChanged = actionListener::onEmailChanged,
                onPasswordChanged = actionListener::onPasswordChanged,
                onSigInPressed = actionListener::onSigInPressed
            )
            SignInSecondaryContent(onGoToSignUp = actionListener::onGoToSignUp)
        }
    }
}
```

This is a Composable function named **SignInScreenContent**, responsible for rendering the content of the sign-in screen. It takes the following parameters:

- **uiState:** The current state of the sign-in screen, containing properties like isLoading, email, and password.
- **onEmailChanged:** A callback function that will be invoked when the email input field changes.
- **onPasswordChanged:** A callback function that will be invoked when the password input field changes.
- **onSignIn:** A callback function that will be invoked when the user clicks the sign-in button.

The **FudgeTvScreenContent** composable is used to create a screen layout. It takes a title resource as a parameter.

Overall, the **SignInScreenContent** composable is responsible for rendering the content of the sign-in screen using Fudge's components. It ensures a consistent UI design and handles user interactions efficiently through callback functions.

## Using FudgeTV UI Library

### Step 1: Configuring Maven Repository

Ensure to add the GitHub Maven repository to your `build.gradle` or `settings.gradle` file to access the dependency hosted on GitHub Packages:

```kotlin
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/sergio11/fudge_tv_compose_library")
        credentials {
            // Here, set your authentication credentials
            username = githubProperties["gpr.usr"] as String? ?: System.getenv("GPR_USER")
            password = githubProperties["gpr.key"] as String? ?: System.getenv("GPR_API_KEY")
        }
    }
}
```

### Step 2: Adding the Dependency to the Project
Once the repository is configured, you can add the "Fudge: Jetpack Compose TV UI Kit" dependency to your project.

```
fudge-tv-compose = "CURRENT_VERSION"
fudge-tv-compose = { module = "com.dreamsoftware.libraries:fudge-tv-compose", version.ref = "fudge-tv-compose" }
```

```kotin
implementation(libs.fudge.tv.compose)
```

## ⚠️ Disclaimer  

This project has been developed **exclusively for learning and experimentation purposes**. **Fudge** is an exploratory Jetpack Compose UI kit designed to gain insights into TV interface development, focus management, and state handling in Android applications.  


## Contribution
Contributions to Fudge: Jetpack Compose TV UI Kit are highly encouraged! If you're interested in adding new features, resolving bugs, or enhancing the project's functionality, please feel free to submit pull requests.

## Credits
Fudge: Jetpack Compose TV UI Kit is developed and maintained by Sergio Sánchez Sánchez (Dream Software). Special thanks to the open-source community and the contributors who have made this project possible. If you have any questions, feedback, or suggestions, feel free to reach out at dreamsoftware92@gmail.com.

## Visitors Count

<img width="auto" src="https://profile-counter.glitch.me/fudge_tv_compose_library/count.svg" />
 
 ## Please Share & Star the repository to keep me motivated.
  <a href = "https://github.com/sergio11/fudge_tv_compose_library/stargazers">
     <img src = "https://img.shields.io/github/stars/sergio11/fudge_tv_compose_library" />
  </a>


## License ⚖️

This project is licensed under the MIT License, an open-source software license that allows developers to freely use, copy, modify, and distribute the software. 🛠️ This includes use in both personal and commercial projects, with the only requirement being that the original copyright notice is retained. 📄

Please note the following limitations:

- The software is provided "as is", without any warranties, express or implied. 🚫🛡️
- If you distribute the software, whether in original or modified form, you must include the original copyright notice and license. 📑
- The license allows for commercial use, but you cannot claim ownership over the software itself. 🏷️

The goal of this license is to maximize freedom for developers while maintaining recognition for the original creators.

```
MIT License

Copyright (c) 2024 Dream software - Sergio Sánchez 

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
