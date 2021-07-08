package com.riquest.sistemmanajemenmenu

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.riquest.sistemmanajemenmenu.home.DashboardMenu
import com.riquest.sistemmanajemenmenu.utils.Preferences
import kotlinx.android.synthetic.main.customer_form.*
import java.util.*


class CustomerForm : AppCompatActivity() {


    private lateinit var sNama: String
    private lateinit var sID: String
    private lateinit var sNomeja: String

    lateinit var mFirebaseInstance : FirebaseDatabase
    lateinit var mDatabaseRefence : DatabaseReference
    lateinit var mDatabase : DatabaseReference
    lateinit var mPreferences : Preferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.customer_form)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabaseRefence = mFirebaseInstance.getReference("User")
        mPreferences = Preferences(applicationContext)

        val number = "1234567890"
        var Idacak = ""
        val length = 5
        val id = Random()
        val text = CharArray(length)

        for (count in 0 until length) {
            text[count] = number[id.nextInt(number.length)]
        }
        for (count in 0 until length) {
            Idacak += text[count]
        }

        sID = et_id.setText("A"+Idacak+"u").toString()

        btn_lanjut.setOnClickListener {
            //val database = FirebaseDatabase.getInstance()
            //val myRef = database.getReference().child("User")

            sNama = et_nama.text.toString()
            sNomeja = spinnermeja.selectedItem.toString()

            if(sNama.equals("")){
                et_nama.error = "Silahkan input nama anda"
                et_nama.requestFocus()
            } else {
                //pushLogin(iNama, iNomeja)
                saveUser(sID, sNama, sNomeja)
            }
//            var intent = Intent(this@CustomerForm, DashboardMenu::class.java)
//            startActivity(intent)
        }
    }

    private fun saveUser(sID: String, sNama: String, sNomeja: String) {
        var user = User()
        user.nama = sNama
        user.id = sID
        user.nomeja = sNomeja

        if(sNama != null){
            checkingNama(sNama, user)
        }
    }

    private fun checkingNama(sNama: String, data: User) {
        mDatabaseRefence.child(sNama).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    mDatabaseRefence.child(sNama).setValue(data)

                    //SETTING REFERENCES
                    mPreferences.setValues("nama", "" + data.nama)
                    mPreferences.setValues("nomeja", "" + data.nomeja)

                    var goDashboardMenu = Intent(this@CustomerForm,
                            DashboardMenu::class.java).putExtra("nama", data.nama)
                    startActivity(goDashboardMenu)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@CustomerForm, "" + databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }

   // private fun pushLogin(iNama: String, iNomeja: String) {
      //  mDatabase.child(iNama).addValueEventListener(object: ValueEventListener{
      ///      override fun onDataChange(dataSnapshot: DataSnapshot) {
       //         var user = dataSnapshot.getValue(User::class.java)
       //         if(user == null){
       //             Toast.makeText(this@CustomerForm, "Nama tidak ditemukan", Toast.LENGTH_LONG).show()
        //        } else {
        //            preference.setValues("id", user.id.toString())
        //            preference.setValues("nama", user.nama.toString())
        //            preference.setValues("nomeja", user.nomeja.toString())
        //            var intent = Intent(this@CustomerForm, DashboardMenu::class.java)
         //           startActivity(intent)
         //       }
        //    }

        //    override fun onCancelled(databaseError: DatabaseError) {
         //       Toast.makeText(this@CustomerForm, databaseError.message, Toast.LENGTH_LONG).show()
        //    }
       // })
    //}
}

