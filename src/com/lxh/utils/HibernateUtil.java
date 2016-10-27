package com.lxh.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.lxh.utils.Base;

public class HibernateUtil {
	private static Configuration conf; 
	Session session = null;
	Transaction ts = null;
	static{
		conf = new Configuration().configure();
	}
	//获取 SessionFactory
	public SessionFactory getSessionFactory (){ 
		return conf.buildSessionFactory();
	}
	//获取 Session
	public Session getSession (){ 
		return getSessionFactory().openSession();
	}
	// 带分页并带:对象属性设置的查询
	public <T> List<T> query (String hql ,Base b){ 
		List<T> list = new ArrayList<T>();
		try {
			session = getSession();
			list = session.createQuery(hql)
					.setProperties(b)
					.setFirstResult((b.getCurPage()-1)*b.getPageSize())
					.setMaxResults(b.getPageSize())
					.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			if(session!=null&&session.isOpen()){
				session.close();
			}
		}
		return list;
	}
	// 带占位符?查询
	public <T> List<T> query (String hql ,Object[] obj){ 
		List<T> list = new ArrayList<T>();
		try {
			session = getSession();
			Query query = session.createQuery(hql);
			for (int i = 0; i < obj.length; i++) {
				query.setParameter(i, obj[i]);
			}
			list = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			if(session!=null&&session.isOpen()){
				session.close();
			}
		}
		return list;
	}
	// 查询
	public <T> List<T> query (String hql){ 
		List<T> list = new ArrayList<T>();
		try {
			session = getSession();
			list = session.createQuery(hql).list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			if(session!=null&&session.isOpen()){
				session.close();
			}
		}
		return list;
	}
	// 带占位符:对象属性查询
	public <T> List<T> query (String hql ,Object obj){ 
		List<T> list = new ArrayList<T>();
		try {
			session = getSession();
			list = session.createQuery(hql).setProperties(obj).list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			if(session!=null&&session.isOpen()){
				session.close();
			}
		}
		return list;
	}
	// 带占位符:自由命名查询
	public <T> List<T> query (String hql ,Map<String,String> map){ 
		List<T> list = new ArrayList<T>();
		try {
			session = getSession();
			Query query = session.createQuery(hql);
			for (Map.Entry<String,String> entry: map.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
			list = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			if(session!=null&&session.isOpen()){
				session.close();
			}
		}
		return list;
	}
	// 获取一条记录
	public Object getOne (Object obj ,int id){ 
		Object o =null;
		try {
			session = getSession();
			o = session.get(obj.getClass(), id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			if(session!=null&&session.isOpen()){
				session.close();
			}
		}
		return o;
	}
	// 获取总数
	public int getTotal (Object obj){ 
		Object o = null;
		try {
			session = getSession();
			o = session.createQuery("select count(*) from "+obj.getClass().getSimpleName())
					.uniqueResult();	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			if(session!=null&&session.isOpen()){
				session.close();
			}
		}
		return Integer.parseInt(o.toString());
	}
	// 保存或修改
	public int saveOrUpdate (Object obj){ 
		try {
			session = getSession();
			ts = session.beginTransaction();
			session.saveOrUpdate(obj);
			ts.commit();
			return 1;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if(ts!=null){
				ts.rollback();
			}
			return 0;
		}finally{
			if(session!=null&&session.isOpen()){
				session.close();
			}
		}
	}
	//删除
	public int delete (Object obj){ 
		try {
			session = getSession();
			ts = session.beginTransaction();
			session.delete(obj);
			ts.commit();
			return 1;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if(ts!=null){
				ts.rollback();
			}
			return 0;
		}finally{
			if(session!=null&&session.isOpen()){
				session.close();
			}
		}	
	}
	//分页
	public <T> List<T> paging (String hql,int curPage , int pageSize){
		List<T> list = new ArrayList<T>();
		try {
			session = getSession();
			list = session.createQuery(hql)
					.setFirstResult((curPage-1)*pageSize)
					.setMaxResults(pageSize)
					.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			if(session!=null&&session.isOpen()){
				session.close();
			}
		}
		return list;
	}
	
	
}


