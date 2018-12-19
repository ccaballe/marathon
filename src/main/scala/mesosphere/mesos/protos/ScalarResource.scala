package mesosphere.mesos.protos

case class ScalarResource(
    name: String,
    value: Double,
    role: Option[String] = None) extends Resource

object ScalarResource {
  def cpus(value: Double, role: Option[String] = None): ScalarResource = ScalarResource(Resource.CPUS, value, role)
  def memory(value: Double, role: Option[String] = None): ScalarResource = ScalarResource(Resource.MEM, value, role)
  def disk(value: Double, role: Option[String] = None): ScalarResource = ScalarResource(Resource.DISK, value, role)
  def gpus(value: Double, role: Option[String] = None): ScalarResource = ScalarResource(Resource.GPUS, value, role)
}
