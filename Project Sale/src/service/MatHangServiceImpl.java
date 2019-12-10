package service;

import java.util.List;

import dao.MatHangDAO;
import dao.MatHangDAOImpl;
import model.MatHang;

public class MatHangServiceImpl implements MatHangService {
	
	private MatHangDAO matHangDAO = null;
	
	public MatHangServiceImpl() {
		this.matHangDAO = new MatHangDAOImpl();
	}

	@Override
	public List<MatHang> getList() {
		// TODO Auto-generated method stub
		return matHangDAO.getList();
	}

}
