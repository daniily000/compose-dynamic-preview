package com.daniily.preview.sample

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daniily.preview.sample.theme.SampleTheme
import com.daniily.preview.sample.view.AppBar
import com.daniily.preview.sample.view.DogCard

data class Dog(
    val name: String,
    val description: String,
    @DrawableRes val image: Int
)

val dogs = listOf(
    Dog("Jack", "Little happy puppy", R.drawable.dog1),
    Dog("Brave", "All in its name – a true guard", R.drawable.dog2),
    Dog("Timmy", "Smart – and very playful", R.drawable.dog3),
)

class AppState {

    private val highlighted: MutableState<Dog?> = mutableStateOf(null)
    private val starred = mutableStateListOf<Dog>()

    fun toggleStar(dog: Dog) {
        if (isStarred(dog)) {
            starred.remove(dog)
        } else {
            starred.add(dog)
        }
    }

    fun isStarred(dog: Dog) = starred.contains(dog)

    fun toggleHighlight(dog: Dog) {
        if (isHighlighted(dog)) {
            highlighted.value = null
        } else {
            highlighted.value = dog
        }
    }

    fun isHighlighted(dog: Dog) = highlighted.value == dog
}

@Preview
@Composable
fun App() {
    val state = remember { AppState() }
    SampleTheme {
        Scaffold(
            topBar = { AppBar("Good Boys") }
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                dogs.forEach { dog ->
                    DogCard(
                        name = dog.name,
                        description = dog.description,
                        image = painterResource(id = dog.image),
                        starred = state.isStarred(dog),
                        highlighted = state.isHighlighted(dog),
                        onClick = { state.toggleHighlight(dog) },
                        onStarClick = { state.toggleStar(dog) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
