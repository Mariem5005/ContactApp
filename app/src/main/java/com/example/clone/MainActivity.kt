package com.example.clone

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clone.ui.Contact
import com.example.clone.ui.ContactList
import com.example.clone.ui.theme.CloneTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CloneTheme {
                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                val ctx = LocalContext.current
                Scaffold(
                    modifier = Modifier
                    .fillMaxSize(),
                    containerColor = Color.White,
                    topBar = {
                        TopAppBar(
                            title = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "Hello")
                                    IconButton(onClick = { OpenDialer(ctx,"010") }) {
                                        Icon(Icons.Rounded.Home, contentDescription = "Home")
                                    }
                                }
                            },
                            scrollBehavior = scrollBehavior
                        )
                    }) {innerPadding ->
                    ContactsList(ContactList().person, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ContactsList(contacts: List<Contact>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.padding(top = 50.dp)
    ) {
        items(contacts) { contact ->
            ShowList(contactPerson = contact)
        }
    }
}
@Composable
fun ShowList(contactPerson: Contact, modifier: Modifier=Modifier){
    val ctx = LocalContext.current
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Image(painter = painterResource(id = contactPerson.image), contentDescription ="photo")
        Text(text = contactPerson.name, textAlign = TextAlign.Center, modifier = Modifier.clickable {
            val phone= contactPerson.number
            OpenDialer(context = ctx, phone = phone)
        })
        SelectionContainer {
            Text(text = contactPerson.number, textAlign = TextAlign.Center)
        }
    }
}
fun OpenDialer(context: Context, phone: String){
    val i = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("phone:${phone}")}
        context.startActivity(i)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CloneTheme {
        val contacts = ContactList().person
        ContactsList(contacts)
    }
}