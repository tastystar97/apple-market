package com.example.applemarketapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.example.applemarketapp.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var isLike = false

    private val itemPosition: Int by lazy {
        intent.getIntExtra(Constants.ITEM_INDEX, 0)
    }

    private val item: SaleItem? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constants.ITEM_INDEX, SaleItem::class.java)
        } else {
            intent.getParcelableExtra<SaleItem>(Constants.ITEM_OBJECT)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivItemImg.setImageDrawable(item?.let {
            ResourcesCompat.getDrawable(resources, it.Image, null)
        })

        binding.tvSellerName.text = item?.SellerName
        binding.tvAddress.text = item?.Address
        binding.tvItemTitle.text = item?.ItemTitle
        binding.tvItemContent.text = item?.ItemDetail
        binding.tvPrice.text = DecimalFormat("#,###").format(item?.Price) + "원"

        isLike = item?.isLike == true
        binding.ivLike.setImageResource(
            if (isLike) {
                R.drawable.ic_heart_filled
            } else {
                R.drawable.ic_heart_empty
            }
        )

        binding.ivBack.setOnClickListener {
            exit()
        }

        binding.llLike.setOnClickListener {
            isLike = if (!isLike) {
                binding.ivLike.setImageResource(R.drawable.ic_heart_filled)
                Snackbar.make(binding.constLayout1, "관심목록에 추가되었습니다.", Snackbar.LENGTH_SHORT).show()
                true
            } else {
                binding.ivLike.setImageResource(R.drawable.ic_heart_empty)
                false
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        exit()
    }

    private fun exit() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("itemIndex", itemPosition)
            putExtra("isLike", isLike)
        }
        setResult(RESULT_OK, intent)
        if (!isFinishing) finish()
    }
}