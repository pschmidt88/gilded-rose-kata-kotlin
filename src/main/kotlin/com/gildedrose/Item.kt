package com.gildedrose

open class Item(var name: String, var sellIn: Int, var quality: Int) {
    override fun toString(): String {
        return this.name + ", " + this.sellIn + ", " + this.quality
    }
}

const val LegendaryItemQuality = 80

class LegendaryItem(name: String, sellIn: Int) : Item(name, sellIn, LegendaryItemQuality)

class AgedBrie(name: String, sellIn: Int, quality: Int) : Item(name, sellIn, quality)

class BackstagePass(name: String, sellIn: Int, quality: Int) : Item(name, sellIn, quality)