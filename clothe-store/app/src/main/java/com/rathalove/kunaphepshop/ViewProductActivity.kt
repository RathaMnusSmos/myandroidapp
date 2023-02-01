package com.rathalove.kunaphepshop

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.rathalove.kunaphepshop.API.APIUserInterface
import com.rathalove.kunaphepshop.API.RetrofitClientInstance
import com.rathalove.kunaphepshop.Adapter.ColorProductDetailAdapter
import com.rathalove.kunaphepshop.DataModel.PojoAllProduct.AllProduct
import com.rathalove.kunaphepshop.databinding.ActivityViewProductBinding
import com.rathalove.kunaphepshop.fragment.HomeFragmmenrt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ViewProductActivity : AppCompatActivity() {

    private var colors: LinkedList<String> = LinkedList()
    private lateinit var binding: ActivityViewProductBinding
    private  var productData: LinkedList<AllProduct> = LinkedList()
    private var slider :ArrayList<com.denzcoskun.imageslider.models.SlideModel> = ArrayList()
    private lateinit var colorRec: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_view_product)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_product)
        binding.viewProductActivity = this
        if(intent.extras != null){
            var pos: Int = intent.extras!!.getInt(HomeFragmmenrt.POS_KEY_ITEM)
            viewProduct(pos)
        }



    }

    private fun viewProduct(pos: Int){
        var retrof = RetrofitClientInstance.getRetrofitInstance().create(APIUserInterface::class.java)
        retrof.getAllProduct().enqueue(object: Callback<ArrayList<AllProduct>> {
            override fun onResponse(
                call: Call<ArrayList<AllProduct>>,
                response: Response<ArrayList<AllProduct>>
            ) {
                if (response.isSuccessful){
                    Log.d("allPro", "data = ${response.body()!!}")
                    for (i in response.body()!!){
                        productData.add(i)
                    }

                    //Auto image detail
                    for(i in productData[pos].item){
                        var link: String = i.image[0].imageLink.toString()
                        slider.add(SlideModel(link))
                    }
                    binding.productImageDetail.setImageList(slider, ScaleTypes.FIT)

                    for(i in productData[pos].item){
                        var color = i.ColorOnSide!!.color!!.colorName.toString()
                        if (!colors.contains(color)){
                            colors.add(color)
                        }
                    }
                    val adapter : ColorProductDetailAdapter = ColorProductDetailAdapter(baseContext, colors)
                    val newArriveLinearLayoutManager: LinearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL, false)
                    colorRec = binding.productColorDetailRecyclerView
                    colorRec.layoutManager = newArriveLinearLayoutManager
                    colorRec.adapter = adapter

                }

            }

            override fun onFailure(call: Call<ArrayList<AllProduct>>, t: Throwable) {
                Log.d("allProductErr", "error = ${t.message}")
            }

        })
    }
}