package com.scolley.roomdemo

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.scolley.roomdemo.database.UserDao
import com.scolley.roomdemo.database.UserDatabase
import com.scolley.roomdemo.database.UserEntity
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private var inputName: String = ""
    private var inputId: Int = 0
    private var inputUser: UserEntity? = null
    private var defaultUser: UserEntity = UserEntity(
        name = "default",
        age = Random.nextInt(1, 20)
    )

    private lateinit var db: UserDatabase
    private lateinit var dao: UserDao
    private lateinit var insertButton: Button
    private lateinit var deleteButton: Button
    private lateinit var queueButton: Button
    private lateinit var clearButton: Button
    private lateinit var tvUsers: TextView
    private lateinit var etInputName: EditText
    private lateinit var etInputId: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buildDatabase(this)
    }

    private fun buildDatabase(context: Context) {
        db = UserDatabase.getInstance(context)
        dao = db.userDao()
    }

    override fun onStart() {
        super.onStart()
        initView()
        initListener()
    }

    private fun initView() {
        tvUsers = findViewById(R.id.tv_users)
        etInputName = findViewById(R.id.et_input_name)
        etInputId = findViewById(R.id.et_input_id)
        insertButton = findViewById(R.id.bt_insert_room)
        deleteButton = findViewById(R.id.bt_delete_room)
        clearButton = findViewById(R.id.bt_clear_room)
        queueButton = findViewById(R.id.bt_queue_room)
    }

    private fun initListener() {

        initTextChangeLister()

        insertButton.setOnClickListener{
            insertUser()
            updateUI(usersToString(queueAllUsers()))
        }

        deleteButton.setOnClickListener{
            deleteUser()
            updateUI(usersToString(queueAllUsers()))
        }

        queueButton.setOnClickListener{
            updateUI(queueOneUser(inputId).toString())
        }

        clearButton.setOnClickListener{
            clearAllUsers()
            updateUI(usersToString(queueAllUsers()))
        }

    }

    private fun initTextChangeLister() {
        etInputName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                inputName = s.toString()
            }
        })

        etInputId.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                inputId = s.toString().toInt()
            }
        })
    }

    private fun insertUser() {
        inputUser = UserEntity(
            name = inputName
        )
        dao.insert(inputUser ?: defaultUser)
    }

    private fun deleteUser() {
        inputUser = UserEntity(
            id = inputId,
            name = inputName
        )
        dao.delete(inputUser ?: defaultUser)
    }

    private fun clearAllUsers() {
        dao.clear()
    }

    private fun queueAllUsers(): List<UserEntity>? {
        return dao.getAllUsers()
    }

    private fun queueOneUser(id: Int): UserEntity? {
        return dao.getUser(id)
    }

    private fun usersToString(list: List<UserEntity>?): String {
        var text = ""
        list?.forEach {
            text += "$it/ "
        }
        return text
    }

    private fun updateUI(text: String){
        tvUsers.text = text
    }


}