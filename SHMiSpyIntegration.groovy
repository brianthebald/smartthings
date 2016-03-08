/**
* Enable/Disable iSpy recordonmotion when SmartHomeMonitor is Armed/Disarmed
*
* Author: Brian Stearns
*/

// Automatically generated. Make future change here.
definition(
name: "SHM and iSpy integration",
namespace: "brianthebald",
author: "brianthebald@gmail.com",
description: "Using SmartThings SmartHomeMonitor arm/disarm status to command ispyconnect to recordonmotion or not, by sending http command from local SmartThings hub",
category: "My Apps",
iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience%402x.png",
iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience%402x.png")

def installed()
{
  subscribe(location, "alarmSystemStatus", alarmHandler)
}

def updated()
{
  unsubscribe()

  subscribe(location, "alarmSystemStatus", alarmHandler)
}

def alarmHandler(evt) {
  def deviceNetworkId = "C0A80163:1F90"  //  "192.168.1.99:8080"
  // The deviceNetworkId is the hex encoded IP and port of the PC running ispy in the home

  def ip = "192.168.1.99:8080"
  // This is the ip of the PC running the ispy application in the home

  def cmd = ""

  if ( evt.value.equals("away") || evt.value.equals("stay") ) {
    cmd = "recordondetecton"
  } else if ( evt.value.equals("off") ) {
    cmd = "recordingoff"
  }

  //http://IPADDRESS:PORT/COMMAND?ot=OBJECT_TYPE&oid=OBJECT_ID
  log.debug "sendHubCommand: ${cmd}"
  sendHubCommand(new physicalgraph.device.HubAction("""GET /${cmd} HTTP/1.1\r\nHOST: $ip\r\n\r\n""", physicalgraph.device.Protocol.LAN, "${deviceNetworkId}"))

}

def locationHandler(evt) {
  def description = evt.description
  def hub = evt?.hubId
}
