/*
 * Copyright (C) 2018 Drake, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.drake.brv.sample.ui.fragment

import androidx.core.content.ContextCompat
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentMultiTypeBinding
import com.drake.brv.sample.databinding.ItemMultiTypeSimpleBinding
import com.drake.brv.sample.databinding.ItemMultiTypeThreeSpanBinding
import com.drake.brv.sample.model.Model
import com.drake.brv.sample.model.ThreeSpanModel
import com.drake.brv.sample.model.TwoSpanModel
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import com.drake.tooltip.toast


class MultiTypeFragment : EngineFragment<FragmentMultiTypeBinding>(R.layout.fragment_multi_type) {

    override fun initView() {
        binding.rv.linear().setup {
            addType<Model>(R.layout.item_multi_type_simple)
            addType<TwoSpanModel>(R.layout.item_multi_type_two_span)
            addType<ThreeSpanModel>(R.layout.item_multi_type_three_span)

            onBind {
                when (itemViewType) {
                    R.layout.item_multi_type_simple -> {
                        val tv = getBinding<ItemMultiTypeSimpleBinding>().tvName
                        tv.setTextColor(ContextCompat.getColor(context, R.color.link_text_color))
                        tv.text = getModel<Model>().itemPosition.toString()
                    }

                    R.layout.item_multi_type_two_span -> {

                    }

                    R.layout.item_multi_type_three_span -> {
                        val model = getModel<ThreeSpanModel>()
                        getBinding<ItemMultiTypeThreeSpanBinding>().rvThree.linear().setup {
                            addType<String>(R.layout.item_multi_type_simple)
                            onBind {
                                getBinding<ItemMultiTypeSimpleBinding>().tvName.text =
                                    getModel<String>()
                            }
                        }.models = model.list

                    }
                }
            }
        }.models = getData()

        // 点击事件
        binding.rv.bindingAdapter.onClick(R.id.item) {
            when (itemViewType) {
                R.layout.item_multi_type_simple -> toast("类型1")
                R.layout.item_multi_type_two_span -> toast("类型2")
            }
        }

    }

    private fun getData(): MutableList<Any> {
        return mutableListOf(
            Model(),
            TwoSpanModel(),
            TwoSpanModel(),
            ThreeSpanModel().apply { list.add("000") },
            Model(),
            ThreeSpanModel().apply {
                list.add("100")
                list.add("100")
            },
            Model(),
            ThreeSpanModel().apply {
                list.add("200")
                list.add("200")
                list.add("200")
            },
            Model(),
            ThreeSpanModel().apply {
                list.add("300")
                list.add("300")
                list.add("300")
                list.add("300")
            },
            Model(),
            ThreeSpanModel().apply {
                list.add("500")
                list.add("600")
                list.add("700")
                list.add("800")
                list.add("900")
            },
            Model()
        )
    }

    override fun initData() {
    }
}
