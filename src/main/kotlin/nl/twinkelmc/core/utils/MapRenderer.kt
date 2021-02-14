package nl.twinkelmc.core.utils

import nl.twinkelmc.core.Main
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class MapRenderer(private val player: Player, private val imgName: String, val loc: Location): BukkitRunnable() {

    var x = 0
    var z = 0
    val img: BufferedImage = ImageIO.read(File("plugins/TMC-Core/${imgName}.png"))

    val chunkSize = 30
    val imgScaleDown = 2

    fun render() {
        this.runTaskTimer(Main.instance, 0L, 1L)
    }

    override fun run() {

        var skip = false

        for (cX in 0 until chunkSize) {
            for (cZ in 0 until chunkSize) {
                if (!skip) {
                    val rX = x * chunkSize + cX
                    val rZ = z * chunkSize + cZ
                    if (hasX(img, rX * imgScaleDown)) {
                        if (hasZ(img, rZ * imgScaleDown)) {
                            if (img.getRGB(rX * imgScaleDown, rZ * imgScaleDown) != 0) {
                                loc.copy().add(rX.toDouble(), 0.0, rZ.toDouble()).block.type = Material.BLACK_CONCRETE
                            } else {
                                loc.copy().add(rX.toDouble(), 0.0, rZ.toDouble()).block.type = Material.WHITE_CONCRETE
                            }

                        } else {
                            loc.copy().add(rX.toDouble(), 0.0, rZ.toDouble()).block.type = Material.RED_CONCRETE
                        }
                    } else if (!hasZ(img, rZ * imgScaleDown)) {
                        Bukkit.broadcastMessage("Runnable ended (1)")
                        this.cancel()
                        return
                    } else {
                        loc.copy().add(rX.toDouble(), 0.0, rZ.toDouble()).block.type = Material.RED_CONCRETE
                        x = 0
                        z++
                        skip = true
                    }
                }
            }
        }
        if (x % 5 == 0) {
            player.teleport(loc.copy().add((x * 50).toDouble(), 0.0, (z * 50).toDouble()))
        }
        if (!skip) {
            x++
            if (!hasX(img, x * chunkSize)) {
                x = 0
                z++
                if (!hasZ(img, z * chunkSize)) {
                    Bukkit.broadcastMessage("Runnable ended (2)")
                    this.cancel()
                    this.cancel()
                    return
                }
            }
        }
    }

    private fun hasX(img: BufferedImage, x: Int): Boolean {
        return try {
            img.getRGB(x, 0)
            true
        } catch (e: ArrayIndexOutOfBoundsException) {
            false
        }
    }

    private fun hasZ(img: BufferedImage, z: Int): Boolean {
        return try {
            img.getRGB(0, z)
            true
        } catch (e: ArrayIndexOutOfBoundsException) {
            false
        }
    }

    private fun Location.copy(): Location {
        return Location(this.world, this.x, this.y, this.z)
    }

    private fun render(player: Player) {
        val img = ImageIO.read(File("plugins/TMC-Core/Efteling_Plattegrond.png"))
        val loc = player.location

        for (i in 0 until img.raster.width / 20 ) {
            for (j in 0 until img.raster.height / 20 ) {
                if (img.getRGB(i, j) != 0) {
                    player.location.add(i.toDouble(), 0.0, j.toDouble()).block.type = Material.STONE
                }
            }
        }

        player.sendMessage("${img.raster.width} ${img.raster.height} ${img.width} ${img.height}")

    }


}