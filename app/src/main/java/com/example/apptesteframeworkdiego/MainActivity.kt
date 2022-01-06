package com.example.apptesteframeworkdiego

import android.graphics.PostProcessor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.apptesteframeworkdiego.model.AlbumsResponseModel
import com.example.apptesteframeworkdiego.model.PostsResponseModel
import com.example.apptesteframeworkdiego.model.TodosResponseModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    // SQLite
    var pessoaList = ArrayList<PostsResponseModel>()
    var database = Database(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btGravar?.setOnClickListener {
            var dado = PostsResponseModel(12,2,"Ola mundo","Bodye")
            database.addPessoa(dado)
        }
        btLer?.setOnClickListener {
            var data = database.pessoas()

            for (item in data){
                tvDados?.setText(tvDados?.text.toString() + "Dados\n"+ item.userId+"\n"+item.id+"\n"+item.title+"\n"+item.body)
            }

        }
    }




    fun getDataPosts(root: View, view: View) {
        val retrofitClient = NetworkUtils
            .getRetrofitInstance( "https://jsonplaceholder.typicode.com")

        val endpoint = retrofitClient.create(NetworkUtils.EndpointPosts::class.java)
        val callback = endpoint.getPosts()

        callback.enqueue(object : Callback<List<PostsResponseModel>> {
            override fun onFailure(call: Call<List<PostsResponseModel>>, t: Throwable) {
                Snackbar.make(view, "Falha no recebimento! ${t.message}", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            override fun onResponse(
                call: Call<List<PostsResponseModel>>,
                response: Response<List<PostsResponseModel>>
            ) {
                response.body()?.forEach {
                   // val edResposta: EditText = root.findViewById(R.id.edResposta)
                   // edResposta.setText("${it.body} \n outros dados: ${it.id} ++ ${it.title} ++ ${it.userId}")

                    Snackbar.make(view, "Dados recebidos! $it.body", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                    //textView.text = textView.text.toString().plus(it.body)
                }
            }
        })
    }

    fun getDataAlbums(root: View, view: View) {
        val retrofitClient = NetworkUtils
            .getRetrofitInstance(  "https://jsonplaceholder.typicode.com" )

        val endpoint = retrofitClient.create(NetworkUtils.EndpointAlbums::class.java)
        val callback = endpoint.getAlbums()

        callback.enqueue(object : Callback<List<AlbumsResponseModel>> {
            override fun onFailure(call: Call<List<AlbumsResponseModel>>, t: Throwable) {
                Snackbar.make(view, "Falha no recebimento! ${t.message}", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            override fun onResponse(
                call: Call<List<AlbumsResponseModel>>,
                response: Response<List<AlbumsResponseModel>>
            ) {
                response.body()?.forEach {
                 //   val edResposta: EditText = root.findViewById(R.id.edResposta)
                 //   edResposta.setText("${it.body} \n outros dados: ${it.id} ++ ${it.title} ++ ${it.userId}")

                    Snackbar.make(view, "Dados recebidos! $it.body", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                    //textView.text = textView.text.toString().plus(it.body)
                }
            }
        })
    }

    fun getDataTodos(root: View, view: View) {
        val retrofitClient = NetworkUtils
            .getRetrofitInstance(  "https://jsonplaceholder.typicode.com/todos")

        val endpoint = retrofitClient.create(NetworkUtils.EndpointTodos::class.java)
        val callback = endpoint.getTodos()

        callback.enqueue(object : Callback<List<TodosResponseModel>> {
            override fun onFailure(call: Call<List<TodosResponseModel>>, t: Throwable) {
                Snackbar.make(view, "Falha no recebimento! ${t.message}", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            override fun onResponse(
                call: Call<List<TodosResponseModel>>,
                response: Response<List<TodosResponseModel>>
            ) {
                response.body()?.forEach {
                //    val edResposta: EditText = root.findViewById(R.id.edResposta)
                //    edResposta.setText("${it.body} \n outros dados: ${it.id} ++ ${it.title} ++ ${it.userId}")

                    Snackbar.make(view, "Dados recebidos! $it.body", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                    //textView.text = textView.text.toString().plus(it.body)
                }
            }
        })
    }
}