package ${packageName};

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ${fullBeanName};
import ${iMapperFullClassName};
import ${iServiceFullClassName};
import ${baseServiceImplPackage}.BaseServiceImpl;
import ${iMapperBasePackage}.BaseMapper;

/**
 * Created by CodeGen v1.2.1 at date:${date?datetime} Powered by Xiaowei.Jiang
 */
@Service
public class ${className} extends BaseServiceImpl<${simpleBeanName},${primaryPropertyType}> implements ${iServiceSimplClassName}{
    @Resource
    ${iMapperSimpleClassName} mapper;
     
    @Override
    protected BaseMapper<${simpleBeanName},${primaryPropertyType}> getMapper() {
    	return mapper;
    }
}