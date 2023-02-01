package com.rathalove.kunaphepshop.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.gson.Gson

import com.rathalove.kunaphepshop.API.APIUserInterface
import com.rathalove.kunaphepshop.API.RetrofitClientInstance
import com.rathalove.kunaphepshop.Adapter.NewArriveAdapter
import com.rathalove.kunaphepshop.Adapter.TopCategoryAdapter
import com.rathalove.kunaphepshop.DataModel.CategoryData
import com.rathalove.kunaphepshop.DataModel.PojoAllProduct.AllProduct
import com.rathalove.kunaphepshop.DataModel.PojoAllProduct.NewArriveItemData
import com.rathalove.kunaphepshop.DataModel.TopCategoryItemData
import com.rathalove.kunaphepshop.DataPojo_AllCategory.AllCategoryData
import com.rathalove.kunaphepshop.Listener.OnItemClickListener
import com.rathalove.kunaphepshop.R
import com.rathalove.kunaphepshop.ViewProductActivity
import com.rathalove.kunaphepshop.databinding.FragmentHomeFragmmenrtBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class HomeFragmmenrt : Fragment(), OnItemClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_fragmmenrt, container, false)
    }

    private lateinit var fragmentBinding : FragmentHomeFragmmenrtBinding
    private lateinit var topCategoryRec: RecyclerView
    private lateinit var newArriveRec: RecyclerView
    private lateinit var colorItemRec : RecyclerView

    private var gson = Gson()
    private var topCategoryLinkedList: LinkedList<TopCategoryItemData> = LinkedList()
    private var newArriveLinkedList: LinkedList<NewArriveItemData> = LinkedList()

    private var categoryData: LinkedList<CategoryData> = LinkedList()
    private var allProductData: LinkedList<AllProduct> = LinkedList()
    private var dataCate: LinkedList<AllCategoryData> = LinkedList()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var binding = FragmentHomeFragmmenrtBinding.bind(view)
        fragmentBinding = binding


        //==========================for top category product================================
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        topCategoryRec = binding.topCategoryItemRecyclerView
        topCategoryRec.layoutManager = linearLayoutManager
        allCategoryData()

        //========================for new arrive product================================

        val newArriveLinearLayoutManager: LinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        newArriveRec = binding.newArriveItemRecyclerView
        newArriveRec.layoutManager = newArriveLinearLayoutManager


        //Auto image banner
        var slider :ArrayList<com.denzcoskun.imageslider.models.SlideModel> = ArrayList()
        slider.add(SlideModel("https://d1csarkz8obe9u.cloudfront.net/posterpreviews/fashion-sale-banner-template-design-389dc7a74f096738d1d425314404a2cd_screen.jpg?ts=1605613724", ScaleTypes.FIT))
        slider.add(SlideModel("https://marketplace.canva.com/EAE8Gq3Ra_o/1/0/1600w/canva-yellow-modern-fashion-%28banner-%28landscape%29%29-Be9zRph_EnI.jpg", ScaleTypes.FIT))
        slider.add(SlideModel("https://img.freepik.com/premium-vector/fashion-banner-background-web-banner-billboard-fashion-promotion-funny-design-concept_142491-93.jpg", ScaleTypes.FIT))
        slider.add(SlideModel("https://marketplace.canva.com/EAFRhWi7a84/1/0/1600w/canva-orange-mega-sale-modern-limited-discount-banner-ylEJ3ng3cAM.jpg", ScaleTypes.FIT))
        binding.imageSlider.setImageList(slider, ScaleTypes.FIT)
        //End Image banner

        getAllProduct()

    }

    //


    //get all category
    private fun allCategoryData(){
        var retrofit = RetrofitClientInstance.getRetrofitInstance().create(APIUserInterface::class.java)
        retrofit.getCategoryData().enqueue(object: Callback<ArrayList<AllCategoryData>>{
            override fun onResponse(
                call: Call<kotlin.collections.ArrayList<AllCategoryData>>,
                response: Response<kotlin.collections.ArrayList<AllCategoryData>>
            ) {
                if (response.isSuccessful){
                    Log.d("pojo", "data = ${response.body()!!}")
                    for(i in response.body()!!){
                        dataCate.add(i)
                    }
                    val adapter: TopCategoryAdapter = TopCategoryAdapter(requireContext(), dataCate, this@HomeFragmmenrt)
                    topCategoryRec.adapter = adapter
                    Log.d("cateData", "Data1 = ${dataCate}")

                }

            }

            override fun onFailure(call: Call<kotlin.collections.ArrayList<AllCategoryData>>, t: Throwable) {
                Log.d("pojo", "fail = ${t.message}")
            }

        })

    }
    //all product
    private fun getAllProduct(){
        var retrof = RetrofitClientInstance.getRetrofitInstance().create(APIUserInterface::class.java)
        retrof.getAllProduct().enqueue(object: Callback<kotlin.collections.ArrayList<AllProduct>>{
            override fun onResponse(
                call: Call<ArrayList<AllProduct>>,
                response: Response<ArrayList<AllProduct>>
            ) {
               if (response.isSuccessful){
                   Log.d("allPro", "data = ${response.body()!!}")
                   for(i in response.body()!!){
                      allProductData.add(i)
                   }
                   val newArriveAdapter: NewArriveAdapter = NewArriveAdapter(requireContext(), allProductData,this@HomeFragmmenrt)
                   newArriveRec.adapter = newArriveAdapter

               }

            }

            override fun onFailure(call: Call<ArrayList<AllProduct>>, t: Throwable) {
                Log.d("allProductErr", "error = ${t.message}")
            }

        })
    }

    fun newArriveItem():LinkedList<NewArriveItemData>{
        var img1 = "https://img.freepik.com/premium-photo/elegant-young-woman-black-evening-party-dress-with-deep-neckline-red-jacket-white-background_481253-3978.jpg?w=2000"
        var img2 = "https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8ZmFzaGlvbiUyMG1vZGVsfGVufDB8fDB8fA%3D%3D&w=1000&q=80"
        var img3 = "https://img.freepik.com/free-photo/portrait-sexy-handsome-fashion-male-model-man-dressed-elegant-suit-posing-street-blue-sky_158538-8275.jpg?w=2000"
        var img4 = "https://img.freepik.com/free-photo/high-fashion-look-glamor-closeup-portrait-beautiful-sexy-stylish-brunette-caucasian-young-woman-model_158538-2772.jpg?w=2000"
        var img5 = "https://img.freepik.com/free-photo/fashion-portrait-young-elegant-woman_1328-2685.jpg?w=2000"

        var colorItem: LinkedList<String> = LinkedList<String>()
        colorItem.add("#D9D9D9")
        colorItem.add("#FF9900")
        colorItem.add("#AF2626")
        colorItem.add("#252525")

       /* var colorItem1: LinkedList<ColorItem> = LinkedList<ColorItem>()
        colorItem1.add(ColorItem(colorItem))*/


        newArriveLinkedList.add(NewArriveItemData("$89.59","Cropped Cardigan",img1,colorItem))
        newArriveLinkedList.add(NewArriveItemData("$29.59","Floral Blouse",img2,colorItem))
        newArriveLinkedList.add(NewArriveItemData("$89.59","Cropped Cardigan",img3,colorItem))
        newArriveLinkedList.add(NewArriveItemData("$89.59","Cropped Cardigan",img4,colorItem))
        newArriveLinkedList.add(NewArriveItemData("$89.59","Cropped Cardigan",img5,colorItem))

        return newArriveLinkedList

    }

    fun createItem(): LinkedList<TopCategoryItemData>{
        var img1 = "https://cdn.pixabay.com/photo/2018/06/25/17/01/fashion-3497408_1280.jpg"
        var img2 = "https://assets.vogue.com/photos/58771eeb4188c6505665851f/master/w_2000,h_3000,c_limit/jon-kortajarena.jpg"
        var img3 = "https://i.guim.co.uk/img/media/cc133eea5f8c98957a5848b042ff6e04a7ce4dc8/0_173_3082_1850/master/3082.jpg?width=1200&height=1200&quality=85&auto=format&fit=crop&s=8b7aebf8ea5470aa662b277af3e024e5"
        var img4 = "https://media.vogue.co.uk/photos/5d54c295ea07410008b99814/master/w_1600%2Cc_limit/original"
        var img5 = "https://thumbs.dreamstime.com/b/fashion-shopping-concept-woman-fur-coat-handbag-white-background-female-model-hold-purse-girl-lady-stylish-177541539.jpg"


        topCategoryLinkedList.add(TopCategoryItemData("Women",img1))
        topCategoryLinkedList.add(TopCategoryItemData("Men",img2))
        topCategoryLinkedList.add(TopCategoryItemData("Bag pack",img3))
        topCategoryLinkedList.add(TopCategoryItemData("Hat",img4))
        topCategoryLinkedList.add(TopCategoryItemData("Bag",img5))

        return topCategoryLinkedList
    }

    override fun onTopCategoryItemClick(pos: Int, data: AllCategoryData) {
        Toast.makeText(context,"${data.categoryId}", Toast.LENGTH_SHORT).show()
    }

    override fun onNewArriveItemClick(pos: Int, data: AllProduct) {
        Toast.makeText(context,"${data.item}", Toast.LENGTH_SHORT).show()
        val bundle = Bundle()
        bundle.putInt(POS_KEY_ITEM, pos)
        var intent = Intent(this.context, ViewProductActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)

    }
    companion object{
        const val POS_KEY_ITEM = "pos item"
    }

}
