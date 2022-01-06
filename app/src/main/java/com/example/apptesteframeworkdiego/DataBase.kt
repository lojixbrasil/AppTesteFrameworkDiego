package com.example.apptesteframeworkdiego

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.apptesteframeworkdiego.model.PostsResponseModel
import java.util.*

class Database (ctx: Context): SQLiteOpenHelper(ctx,DB_NAME,null,DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ($TITLE TEXT, $ID INTEGER PRIMARY KEY, $USERID INTEGER, $BODY TEXT);"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addPessoa(pessoa: PostsResponseModel): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(USERID, pessoa.userId)
        values.put(ID, pessoa.id)
        values.put(TITLE, pessoa.title)
        values.put(BODY, pessoa.body)

        val _success = db.insert(TABLE_NAME,null,values)
        return (("$_success").toInt() != -1)
    }

    @SuppressLint("Range")
    fun getPessoa(_id: Int): PostsResponseModel {
        val pessoa = PostsResponseModel()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)
        cursor?.moveToFirst()

        pessoa.userId = cursor.getInt(cursor.getColumnIndex(USERID))
        pessoa.id = cursor.getInt(cursor.getColumnIndex(ID))
        pessoa.title = cursor.getString(cursor.getColumnIndex(TITLE))
        pessoa.body = cursor.getString(cursor.getColumnIndex(BODY))

        cursor.close()
        return pessoa
    }

    @SuppressLint("Range")
    fun pessoas(): ArrayList<PostsResponseModel> {
        val pessoaList = ArrayList<PostsResponseModel>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    val pessoa = PostsResponseModel()
                    pessoa.userId = cursor.getInt(cursor.getColumnIndex(USERID))
                    pessoa.id = cursor.getInt(cursor.getColumnIndex(ID))
                    pessoa.title = cursor.getString(cursor.getColumnIndex(TITLE))
                    pessoa.body = cursor.getString(cursor.getColumnIndex(BODY))

                    pessoaList.add(pessoa)
                }while(cursor.moveToNext())
            }
        }
        cursor.close()
        return pessoaList
    }

    fun updatePessoa(pessoa: PostsResponseModel): Boolean{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(USERID, pessoa.userId)
            put(ID, pessoa.id)
            put(TITLE, pessoa.title)
            put(BODY, pessoa.body)
        }
        val _success = db.update(TABLE_NAME, values, "$ID=?", arrayOf(pessoa.id.toString())).toLong()
        db.close()
        return ("$_success").toInt() != -1
    }

    fun deletePessoa(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, "$ID=?", arrayOf(_id.toString())).toLong()
        return ("$_success").toInt() != -1
    }

    fun deleteAllPessoa(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, null,null).toLong()
        db.close()
        return ("$_success").toInt() != -1
    }

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "Testeframework"
        private val TABLE_NAME = "DadosPosts"

        private val USERID = "userId"
        private val ID = "Id"
        private val TITLE = "Title"
        private val BODY = "Body"
    }
}