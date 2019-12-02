import scala.io.Source

object Day01 {

  def main(args: Array[String]): Unit = {
    val source = Source.fromFile(args(0))
    val masses = source.getLines.map(l => l.toInt).toList

    println(masses.map(fuelForMass).sum)
    println(masses.map(fuelForMassAndFuel).sum)

    source.close()
  }

  /**
   * Calculate the required fuel for the given mass.
   *
   * @param mass the given mass
   * @return the required fuel
   */
  def fuelForMass(mass: Int): Int = (Math.floor(mass / 3) - 2).toInt

  /**
   * Calculate the required fuel for the given mass.
   * We also add the required fuel for the mass of the fuel.
   *
   * @param mass the given mass
   * @return the required fuel
   */
  def fuelForMassAndFuel(mass: Int): Int = {
    var requiredFuelAll = 0
    var requiredFuel = fuelForMass(mass)
    while (requiredFuel > 0) {
      requiredFuelAll += requiredFuel
      requiredFuel = fuelForMass(requiredFuel)
    }
    requiredFuelAll
  }
}
