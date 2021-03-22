package com.gildedrose

class GildedRose(var items: Array<Item>) {
    private val itemList: List<Item> = items.map {
        when {
            it.name == "Sulfuras, Hand of Ragnaros" -> LegendaryItem(it.name, it.sellIn)
            it.name == "Aged Brie" -> AgedBrie(it.name, it.sellIn, it.quality)
            it.name.startsWith("Backstage passes") -> BackstagePass(it.name, it.sellIn, it.quality)
            else -> it
        }
    }.toList()

    fun updateQuality() {
        itemList.forEach { item ->
            when (item) {
                // "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
                is LegendaryItem -> return@forEach
            }

            item.sellIn--

            adjustQuality(item)
        }

        items = itemList.toTypedArray()
    }

    private fun looseQuality(item: Item) {
        item.quality = 0
    }

    private fun adjustQuality(item: Item) {
        if (item.name != "Aged Brie" && item.name != "Backstage passes to a TAFKAL80ETC concert") {
            decreaseQuality(item)
        } else {
            // increase quality of aged brie and backstage passes
            increaseQuality(item)

            if (item.name == "Backstage passes to a TAFKAL80ETC concert") {
                if (item.sellIn < 11) {
                    increaseQuality(item)
                }

                if (item.sellIn < 6) {
                    increaseQuality(item)
                }
            }
        }

        if (item.sellIn < 0) {
            when (item) {
                is BackstagePass -> looseQuality(item)
                is AgedBrie -> increaseQuality(item)
                else -> decreaseQuality(item)
            }
        }
    }

    private fun decreaseQuality(item: Item) {
        if (item.quality > 0) {
            item.quality--
        }
    }

    private fun increaseQuality(item: Item) {
        if (item.quality < 50) {
            item.quality++
        }
    }
}
