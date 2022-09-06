package com.example.jetpackcomposetest

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposetest.ui.theme.JetpackComposeTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTestTheme {
                Conversation(SampleData.conversationSample)
            }
        }
    }
}

@Composable
fun MessageCard(msg: Message){
    Row(modifier = Modifier
        .padding(all = 8.dp)
        .background(MaterialTheme.colors.background)
    ){
        Image(painter = painterResource(id = R.drawable.img_1),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)      //把图片显示方式变成圆形
                .size(40.dp)        //修改图片大小
        )

        Spacer(modifier = Modifier.width(8.dp)) //添加间距
        var isExpanded by remember{ mutableStateOf(false)}
        val surfaceColor: androidx.compose.ui.graphics.Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        )

        Column{
            Text(text = msg.author,
            color = MaterialTheme.colors.secondaryVariant   //修改颜色
            )

            Spacer(modifier = Modifier.height(4.dp))    //修改间距

            Surface(color = surfaceColor,
            shape = MaterialTheme.shapes.medium,    //设置形状为圆角
            elevation = 1.dp,
            modifier = Modifier.animateContentSize()
                .padding(1.dp)) {
                Text(text = msg.body,
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { isExpanded = !isExpanded },
                    style = MaterialTheme.typography.body2, //设置字体
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1
                )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>){
    //带缓冲的列表
    LazyColumn{
        items(messages){ messages ->
            MessageCard(msg = messages)
        }
    }
}

@Preview
@Composable
fun PreviewMessageCard(){

}

data class Message(val author: String,val body: String)