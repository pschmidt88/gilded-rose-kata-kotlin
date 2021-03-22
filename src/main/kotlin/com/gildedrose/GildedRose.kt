package com.gildedrose

class GildedRose(var items: Array<Item>) {

    private fun Item.decrementSellIn() {
        this.sellIn--
    }

    fun updateQuality() {
        items.forEach { item ->
            // decrease quality of "normal" items by 1
            if (item.name != "Aged Brie" && item.name != "Backstage passes to a TAFKAL80ETC concert") {
                if (item.quality > 0) {
                    if (item.name != "Sulfuras, Hand of Ragnaros") {
                        item.quality--
                    }
                }
            } else {
                // increase quality of aged brie and backstage passes
                if (item.quality < 50) {
                    item.quality++

                    if (item.name == "Backstage passes to a TAFKAL80ETC concert") {
                        if (item.sellIn < 11) {
                            if (item.quality < 50) {
                                item.quality++
                            }
                        }

                        if (item.sellIn < 6) {
                            if (item.quality < 50) {
                                item.quality++
                            }
                        }
                    }
                }
            }

            // decrement sellin if it is not sulfuras
            if (item.name != "Sulfuras, Hand of Ragnaros") {
                item.decrementSellIn()
            }

            if (item.sellIn < 0) {
                if (item.name != "Aged Brie") {
                    if (item.name != "Backstage passes to a TAFKAL80ETC concert") {
                        if (item.quality > 0) {
                            if (item.name != "Sulfuras, Hand of Ragnaros") {
                                // decrease quality of "normal" items by 1 again, in total decreased quality by 2
                                item.quality--
                            }
                        }
                    } else {
                        // backstage tickets loose their quality after the concert
                        item.quality = 0
                    }
                } else {
                    // increase quality of aged brie and backstage passes again, in total 2, (or 3, or 4 for backstage passes)
                    if (item.quality < 50) {
                        item.quality++
                    }
                }
            }
        }
    }
}
