package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    @Test
    internal fun `it works with multiple items`() {
        val items = arrayOf(
            Item("+5 Dexterity Vest", 10, 20),
            Item("Elixir of the Mongoose", 5, 7),
        )

        val app = GildedRose(items)

        app.updateQuality()

        assertEquals("+5 Dexterity Vest", app.items[0].name)
        assertEquals(9, app.items[0].sellIn)
        assertEquals(19, app.items[0].quality)

        assertEquals("Elixir of the Mongoose", app.items[1].name)
        assertEquals(4, app.items[1].sellIn)
        assertEquals(6, app.items[1].quality)
    }

    @Test
    internal fun `it decreases sellIn and quality of a normal items correctly for each day`() {
        val items = arrayOf(
            Item("+5 Dexterity Vest", 10, 20),
        )

        val app = GildedRose(items)

        app.updateQuality()

        assertEquals("+5 Dexterity Vest", app.items[0].name)
        assertEquals(9, app.items[0].sellIn)
        assertEquals(19, app.items[0].quality)

        app.updateQuality()

        assertEquals("+5 Dexterity Vest", app.items[0].name)
        assertEquals(8, app.items[0].sellIn)
        assertEquals(18, app.items[0].quality)
    }

    @Test
    internal fun `it increases quality of 'Aged Brie' by one for every day passed`() {
        val items = arrayOf(
            Item("Aged Brie", 10, 0),
        )

        val app = GildedRose(items)

        app.updateQuality()

        assertEquals("Aged Brie", app.items[0].name)
        assertEquals(9, app.items[0].sellIn)
        assertEquals(1, app.items[0].quality)

        app.updateQuality()

        assertEquals("Aged Brie", app.items[0].name)
        assertEquals(8, app.items[0].sellIn)
        assertEquals(2, app.items[0].quality)
    }

    @Test
    internal fun `it doesn't decrease quality value of 'Sulfuras, Hand of Ragnaros'`() {
        val items = arrayOf(
            Item("Sulfuras, Hand of Ragnaros", 5, 80),
        )

        val app = GildedRose(items)

        app.updateQuality()

        assertEquals("Sulfuras, Hand of Ragnaros", app.items[0].name)
        assertEquals(5, app.items[0].sellIn)
        assertEquals(80, app.items[0].quality)

        app.updateQuality()

        assertEquals("Sulfuras, Hand of Ragnaros", app.items[0].name)
        assertEquals(5, app.items[0].sellIn)
        assertEquals(80, app.items[0].quality)
    }

    @Test
    internal fun `when the sell by date has passed, then quality degrades twice as fast for normal items`() {
        val items = arrayOf(
            Item("+5 Dexterity Vest", 0, 20),
        )

        val app = GildedRose(items)

        app.updateQuality()

        assertEquals("+5 Dexterity Vest", app.items[0].name)
        assertEquals(-1, app.items[0].sellIn)
        assertEquals(18, app.items[0].quality)

        app.updateQuality()

        assertEquals("+5 Dexterity Vest", app.items[0].name)
        assertEquals(-2, app.items[0].sellIn)
        assertEquals(16, app.items[0].quality)
    }

    @Test
    internal fun `The Quality of an item is never negative`() {
        val items = arrayOf(
            Item("+5 Dexterity Vest", 2, 0),
        )

        val app = GildedRose(items)

        app.updateQuality()

        assertEquals("+5 Dexterity Vest", app.items[0].name)
        assertEquals(1, app.items[0].sellIn)
        assertEquals(0, app.items[0].quality)

        app.updateQuality()

        assertEquals("+5 Dexterity Vest", app.items[0].name)
        assertEquals(0, app.items[0].sellIn)
        assertEquals(0, app.items[0].quality)
    }

    @Test
    internal fun `The Quality of an item is never more than 50`() {
        val items = arrayOf(
            Item("Aged Brie", 2, 49),
            Item("Aged Brie", -2, 40),
        )

        val app = GildedRose(items)

        app.updateQuality()

        assertEquals("Aged Brie", app.items[0].name)
        assertEquals(1, app.items[0].sellIn)
        assertEquals(50, app.items[0].quality)

        assertEquals("Aged Brie", app.items[1].name)
        assertEquals(-3, app.items[1].sellIn)
        assertEquals(42, app.items[1].quality)

        app.updateQuality()

        assertEquals("Aged Brie", app.items[0].name)
        assertEquals(0, app.items[0].sellIn)
        assertEquals(50, app.items[0].quality)

        app.updateQuality()

        assertEquals("Aged Brie", app.items[0].name)
        assertEquals(-1, app.items[0].sellIn)
        assertEquals(50, app.items[0].quality)

        app.updateQuality()

        assertEquals("Aged Brie", app.items[0].name)
        assertEquals(-2, app.items[0].sellIn)
        assertEquals(50, app.items[0].quality)
    }

    /*
    - "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
	Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
	Quality drops to 0 after the concert
     */
    @Test
    internal fun `backstage passes increase in quality by one if the sell in is greater than 10 days`() {
        val items = arrayOf(
            Item("Backstage passes to a TAFKAL80ETC concert", 12, 5),
        )

        val app = GildedRose(items)

        app.updateQuality()

        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name)
        assertEquals(11, app.items[0].sellIn)
        assertEquals(6, app.items[0].quality)
    }

    @Test
    internal fun `backstage passes increase in quality by two if the sell in than 10 days or less`() {
        val items = arrayOf(
            Item("Backstage passes to a TAFKAL80ETC concert", 10, 5),
        )

        val app = GildedRose(items)

        app.updateQuality()

        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name)
        assertEquals(9, app.items[0].sellIn)
        assertEquals(7, app.items[0].quality)
    }

    @Test
    internal fun `backstage passes increase in quality by 3 if the sell in than 10 days or less`() {
        val items = arrayOf(
            Item("Backstage passes to a TAFKAL80ETC concert", 5, 5),
        )

        val app = GildedRose(items)

        app.updateQuality()

        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name)
        assertEquals(4, app.items[0].sellIn)
        assertEquals(8, app.items[0].quality)
    }

    @Test
    internal fun `backstage passes quality drop to zero if after the concert`() {
        val items = arrayOf(
            Item("Backstage passes to a TAFKAL80ETC concert", 0, 40),
        )

        val app = GildedRose(items)

        app.updateQuality()

        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name)
        assertEquals(-1, app.items[0].sellIn)
        assertEquals(0, app.items[0].quality)
    }
}


