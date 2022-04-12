package com.example.telegram.ui.message_recycler_view.view_holders

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.telegram.database.CURRENT_UID
import com.example.telegram.ui.message_recycler_view.views.MessageView
import com.example.telegram.utilits.asTime
import com.example.tg.R


class HolderTextMessage(view: View) : RecyclerView.ViewHolder(view), MessageHolder {
    private  val blocUserMessage: ConstraintLayout = view.findViewById(R.id.bloc_user_message)
    private  val chatUserMessage: TextView = view.findViewById(R.id.chat_user_message)
    private  val chatUserMessageTime: TextView = view.findViewById(R.id.chat_user_message_time)
    private  val blocReceivedMessage: ConstraintLayout = view.findViewById(R.id.bloc_received_message)
    private  val chatReceivedMessage: TextView = view.findViewById(R.id.chat_received_message)
    private  val chatReceivedMessageTime: TextView = view.findViewById(R.id.chat_received_message_time)


    override fun drawMessage(view: MessageView) {
        if (view.from == CURRENT_UID) {
            blocUserMessage.visibility = View.VISIBLE
            blocReceivedMessage.visibility = View.GONE
            chatUserMessage.text = view.text
            chatUserMessageTime.text =
                view.timeStamp.asTime()
        } else {
            blocUserMessage.visibility = View.GONE
            blocReceivedMessage.visibility = View.VISIBLE
            chatReceivedMessage.text = view.text
            chatReceivedMessageTime.text =
                view.timeStamp.asTime()
        }

    }

    override fun onAttach(view: MessageView) {
    }

    override fun onDetach() {
    }
}