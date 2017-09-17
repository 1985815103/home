package ${baseServiceImplPackage};

import java.util.List;
import java.util.Map;

import ${iServiceBasePackage}.BaseService;
import ${iMapperBasePackage}.BaseMapper;
import org.apache.ibatis.session.RowBounds;

/**
 * Created by CodeGen v1.2.1 at date:${date?datetime} Powered by Xiaowei.Jiang
 */
public abstract class BaseServiceImpl<T,K> implements BaseService<T,K> {
	protected abstract BaseMapper<T,K> getMapper();

	@Override
	public int insert(T entity) {
		return getMapper().insert(entity);
	}
	
	@Override
	public int batchInsert(List<T> entities) {
		return getMapper().batchInsert(entities);
	}

	@Override
	public T get(K key) {
		return getMapper().get(key);
	}

	@Override
	public List<T> list(Map<String, Object> paras) {
		return getMapper().list(paras, RowBounds.DEFAULT);
	}

	@Override
	public List<T> list(Map<String, Object> paras, RowBounds rowBounds) {
		return getMapper().list(paras, rowBounds);
	}

	@Override
	public List<Map<String, Object>> listMap(Map<String, Object> paras) {
		return getMapper().listMap(paras, RowBounds.DEFAULT);
	}

	@Override
	public List<Map<String, Object>> listMap(Map<String, Object> paras, RowBounds rowBounds) {
		return getMapper().listMap(paras, rowBounds);
	}

	@Override
	public int delete(K key) {
		return getMapper().delete(key);
	}

	@Override
	public int deleteByMap(Map<String, Object> paras) {
		return getMapper().deleteByMap(paras);
	}

	@Override
	public int update(T entity) {
		return getMapper().update(entity);
	}

	@Override
	public int updateByMap(Map<String, Object> paras) {
		return getMapper().updateByMap(paras);
	}
}