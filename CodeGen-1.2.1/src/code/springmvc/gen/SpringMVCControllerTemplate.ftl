package ${packageName};

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ${iServiceFullClassName};

/**
 * Created by CodeGen v1.2.1 at date:${date?datetime} Powered by Xiaowei.Jiang
 */
@Controller
@RequestMapping("/${requestMapping?uncap_first}")
public class ${className} {
    
    @Resource
    private ${iServiceSimpleClassName} ${iServiceSimpleClassName?uncap_first};
    
    /**
     * 转向
     */
    @RequestMapping("/forward")
	public ModelAndView forward(HttpServletRequest request){
		//TODO 
		String view = null; 
		ModelAndView mv = new ModelAndView(view); 
		Object model = null;
		mv.addObject(model);
		
		return mv;
	}
	
}