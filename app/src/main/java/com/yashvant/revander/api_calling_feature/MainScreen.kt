package com.yashvant.revander.api_calling_feature

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.skydoves.orbital.Orbital
import com.skydoves.orbital.animateBounds
import com.skydoves.orbital.animateMovement
import com.skydoves.orbital.animateTransformation
import com.skydoves.orbital.rememberContentWithOrbitalScope
import com.skydoves.orbital.rememberMovableContentOf
import com.yashvant.revander.api_calling_feature.models.ProfileModel
import com.yashvant.revander.utils.MockUtils

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Simple API Request",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            )
        },
        content = {

            val scrollstate = rememberScrollState()

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OrbitalLazyColumnSample()

                /*val movementSpec = SpringSpec<IntOffset>(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = 200f
                )

                var isTransformed by rememberSaveable { mutableStateOf(false) }
                val poster = rememberContentWithOrbitalScope {
                    GlideImage(
                        modifier = if (isTransformed) {
                            Modifier.size(360.dp, 620.dp)
                        } else {
                            Modifier.size(130.dp, 220.dp)
                        }.animateMovement(this, movementSpec),
                        model = "https://assets.architecturaldigest.in/photos/60004a09d68a278e29c86a11/16:9/w_2560%2Cc_limit/feature6-1366x768.jpg",
                        contentScale = ContentScale.Fit,
                        contentDescription = null
                    )
                }

                Orbital(
                    modifier = Modifier
                        .clickable { isTransformed = !isTransformed }
                ) {
                    if (isTransformed) {
                        Column(
                            Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            poster()
                        }
                    } else {
                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            poster()
                        }
                    }
                }*/

                val id = remember {
                    mutableStateOf(TextFieldValue())
                }

                val profile = remember {
                    mutableStateOf(
                        ProfileModel(
                        age = "",
                        name = "",
                        email = ""
                    )
                    )
                }

                Text(
                    text="API Sample",
                    style= TextStyle(
                        fontSize = 40.sp,
                        fontFamily = FontFamily.Cursive
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    label = { Text(text = "User ID")},
                    value = id.value,
                    onValueChange = { id.value = it }
                )

                Spacer(modifier = Modifier.height(15.dp))

                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            val data = sendRequest(
                                id = id.value.text,
                                profileState = profile
                            )

                            Log.d("Main Activity", profile.toString())
                        }
                    ) {
                        Text(text = "Get Data")
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(text = profile.component1().toString(), fontSize = 40.sp)
            }
        }
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun OrbitalLazyColumnSample() {
    val mocks = MockUtils.getMockPosters()

    Orbital {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(8) { poster ->
                var expanded by rememberSaveable { mutableStateOf(false) }
                AnimatedVisibility(
                    remember { MutableTransitionState(false) }
                        .apply { targetState = true },
                    enter = fadeIn(),
                ) {
                    Orbital(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            expanded = !expanded
                        }) {
                        val title = rememberMovableContentOf {
                            Column(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .animateBounds(Modifier),
                            ) {
                                Text(
                                    text = "Poster name",
                                    fontSize = 18.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                )

                                Text(
                                    text = "This is description",
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                        val image = rememberMovableContentOf {
                            GlideImage(
                                model = "https://www.liveabout.com/thmb/APMQQFMHcHHnJyXnZntsFDu0RLo=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/peter_2008_v2F_hires1-56a00f083df78cafda9fdcb6.jpg",
                                transition = CrossFade
                                ,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .animateBounds(
                                        if (expanded) {
                                            Modifier.fillMaxWidth()
                                        } else {
                                            Modifier.size(80.dp)
                                        },
                                        spring(stiffness = Spring.StiffnessLow),
                                    )
                                    .clip(RoundedCornerShape(20.dp)),
                                contentDescription = null
                            )
                        }

                        if (expanded) {
                            Card(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 20.dp)
                                    .fillMaxWidth()
                            ) {
                                Column {
                                    image()
                                    title()
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .padding(horizontal = 16.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        } else {
                            Card(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 20.dp)
                                    .fillMaxWidth()
                            ) {
                                Row {
                                    image()
                                    title()
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .padding(horizontal = 16.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}
