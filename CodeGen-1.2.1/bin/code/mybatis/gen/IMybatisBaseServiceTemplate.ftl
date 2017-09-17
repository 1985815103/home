package ${iBaseServicePackage};

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
/**
 * Created by CodeGen v1.2.1 at date:${date?datetime} Powered by Xiaowei.Jiang
 */
public interface BaseService<T, K> {
    
    public int insert(T entity);
    
    public int batchInsert(List<T> entities);
    
    public T get(K key);
    
    public List<T> list(Map<String,Object> paras);
    
    public List<T> list(Map<String,Object> paras, RowBounds rowBounds);
    
    public List<Map<String,Object>> listMap(Map<String,Object> paras);
    
    public List<Map<String,Object>> listMap(Map<String,Object> paras, RowBounds rowBounds);
    
    public int delete(K key);
    
    public int deleteByMap(Map<String,Object> paras);
    
    public int update(T entity);
    
	public int updateByMap(Map<String,Object> paras);    
}