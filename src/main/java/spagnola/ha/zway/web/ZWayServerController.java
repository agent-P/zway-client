/**
 * 
 */
package spagnola.ha.zway.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import spagnola.ha.zway.service.ZWayControllerService;


/**
 * @author spagnola
 *
 */
@Controller
public class ZWayServerController {
    @Autowired
    private ZWayControllerService zWayControllerService;
    
    @RequestMapping("/controller")
    @ResponseBody
    public String zWayController() {
        return this.zWayControllerService.getZWayController();
    }
    
    @RequestMapping("/devices")
    @ResponseBody
    public String zWayDevices() {
        return this.zWayControllerService.getZWayDevices();
    }
    
    @RequestMapping("/devices/{deviceNumber}")
    @ResponseBody
    public String zWayDevices(@PathVariable final String deviceNumber) {
        return this.zWayControllerService.getZWayDevice(deviceNumber);
    }
    
    @RequestMapping("ZAutomation/api/devices")
    @ResponseBody
    public String zWayVirtualDevices() {
        return this.zWayControllerService.getZWayVirtualDevices();
    }
    
    @RequestMapping("ZAutomation/api/devices/{deviceId}/command/{command}")
    @ResponseBody
    public String commandZWayVirtualDevices(@PathVariable final String deviceId, @PathVariable final String command) {
        return this.zWayControllerService.commandZWayVirtualDevices(deviceId, command);
    }
    
    
    @RequestMapping("zway/ef/devices/{deviceId}")
    @ResponseBody
    public void updateZWayVirtualDevices(@PathVariable final String deviceId, @RequestBody String deviceInfo) {
        this.zWayControllerService.updateZWayVirtualDevice(deviceId, deviceInfo);
    }
    
    
}
