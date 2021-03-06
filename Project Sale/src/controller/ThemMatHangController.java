package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.ChiTietHoaDon;
import model.MatHang;
import service.MatHangService;
import service.MatHangServiceImpl;
import ultility.AutoCompletion;

public class ThemMatHangController {
	private JTextField textFieldMaMatHang;
	private JTextField textFieldSoLuong;
	private JTextField textFieldDonGia;
	private JTextField textFieldThanhTien;
	private JTextField textFieldLoaiHang;
	private JTextField textFieldConLai;
	private JButton btnThemMatHang;
	private JComboBox<String> comboBox;
	
	//flag
	boolean flag = false;
	
	private MatHangService matHangService = null;
	
	ChiTietHoaDon chiTietHoaDon = null;
	
	public ThemMatHangController(JTextField textFieldMaMatHang, JTextField textFieldSoLuong, JTextField textFieldDonGia,
			JTextField textFieldThanhTien, JTextField textFieldLoaiHang,JTextField textFieldConLai, JButton btnThemMatHang,
			JComboBox<String> comboBox) {
		super();
		this.textFieldMaMatHang = textFieldMaMatHang;
		this.textFieldSoLuong = textFieldSoLuong;
		this.textFieldDonGia = textFieldDonGia;
		this.textFieldThanhTien = textFieldThanhTien;
		this.textFieldLoaiHang = textFieldLoaiHang;
		this.textFieldConLai = textFieldConLai;
		this.btnThemMatHang = btnThemMatHang;
		this.comboBox = comboBox;
		
		this.matHangService = new MatHangServiceImpl();
	}
	
	//Tạo chi tiết hoá đơn mới đổ vào frame mặt hàng
	public void setData(ChiTietHoaDon chiTietHoaDon,JFrame frame) {
		
		this.chiTietHoaDon = chiTietHoaDon;
		
		textFieldMaMatHang.setEditable(false);
		textFieldLoaiHang.setEditable(false);
		textFieldDonGia.setEditable(false);
		textFieldThanhTien.setEditable(false);
		textFieldConLai.setEditable(false);
		
		AutoCompletion.enable(comboBox);
		
		//add các mặt hàng vào trong combobox
		List<MatHang> listItem = matHangService.getList();
		for (MatHang mh : listItem) {
			comboBox.addItem(mh.getTen_mat_hang());
		}
		
		//lấy info mặt hàng đầu tiên trong combobox rồi gán vào các ô textfield
		//đây là do khi hiển thị lúc mới mở frame thì combobox sẽ tự động chọn item đầu tiên add vào
		MatHang matHang = matHangService.getMatHangInfo(comboBox.getItemAt(0));
		System.out.println(comboBox.getName());
		textFieldMaMatHang.setText("#"+Integer.toString(matHang.getMa_mat_hang()));
		textFieldLoaiHang.setText(matHang.getLoai_hang());
		textFieldDonGia.setText(Integer.toString(matHang.getDon_gia()));
		textFieldConLai.setText(Integer.toString(matHang.getTon_kho()));
				
		//sự kiện khi thay đổi item thì nó sẽ ảnh hưởng đến các textfield khác
		comboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {	
				// TODO Auto-generated method stub
				MatHang matHang = matHangService.getMatHangInfo(e.getItem().toString());
				textFieldMaMatHang.setText("#"+Integer.toString(matHang.getMa_mat_hang()));
				textFieldLoaiHang.setText(matHang.getLoai_hang());
				textFieldDonGia.setText(Integer.toString(matHang.getDon_gia()));
				textFieldConLai.setText(Integer.toString(matHang.getTon_kho()));
				
				int thanhtien = Integer.parseInt(textFieldSoLuong.getText()) * Integer.parseInt(textFieldDonGia.getText());
				textFieldThanhTien.setText(Integer.toString(thanhtien));
			}
		});		
		
		//Mỗi khi số lượng được nhập vào, thì thành tiền sẽ được nhân ra tương ứng, nếu trong trường hợp nhập số lượng = 0 hay không ô số lượng trống sẽ gán thành tiền bằng 0
		//Tránh một số trường hợp người dùng cố tình add đơn không số lượng --> đơn hàng ảo
		textFieldSoLuong.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {	//Khi xoá một kí tự
				try {
					if(!textFieldSoLuong.getText().equalsIgnoreCase("")) {
						if(textFieldSoLuong.getText().equals("0")) {
							textFieldThanhTien.setText("");
						} else {
							int thanhtien = Integer.parseInt(textFieldSoLuong.getText()) * Integer.parseInt(textFieldDonGia.getText());
							textFieldThanhTien.setText(Integer.toString(thanhtien));
						}					
					} else {
						textFieldThanhTien.setText("");
					}
					
				} catch (NumberFormatException e2) {
					// TODO: handle exception
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "Bạn ơi đừng nhập chữ vào ô thành tiền", "Lỗi cố tình", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					if(!textFieldSoLuong.getText().equalsIgnoreCase("")) {	//Khi thêm 1 kí tự
						if(textFieldSoLuong.getText().equals("0")) {
							textFieldThanhTien.setText("");
						} else {
							int thanhtien = Integer.parseInt(textFieldSoLuong.getText()) * Integer.parseInt(textFieldDonGia.getText());
							textFieldThanhTien.setText(Integer.toString(thanhtien));
						}					
					} else {
						textFieldThanhTien.setText("");
					}
				} catch (NumberFormatException e2) {
					// TODO: handle exception
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "Bạn ơi đừng nhập chữ vào ô thành tiền", "Lỗi cố tình", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub	
			}
		});
		//set Event
		setEvent(frame);
		
	}
	
	//Sự kiện cho phím thêm mặt hàng khi phím được kích hoạt
	public void setEvent(JFrame frame) {
		btnThemMatHang.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Đoạn này vì mã mặt hàng có thêm dấu thừa "#" nên phải cắt bớt nó khi muốn láy số ra :)))
				MatHang matHang = matHangService.getMatHangInfoByMaMatHang(Integer.parseInt((textFieldMaMatHang.getText().substring(1))));
				
				//Kiểm tra xem số lượng muốn lấy có lớn hơn tồn kho, nếu mà lớn hơn thì sẽ xuất ra lỗi và không cho hoàn thành việc thêm đơn hàng
				int ton_kho = matHang.getTon_kho();
				int so_luong = Integer.parseInt(textFieldSoLuong.getText());
				ton_kho -= so_luong;
				if(ton_kho < 0 ) {
					JOptionPane.showMessageDialog(null, "Số lượng tồn kho của bạn không đủ", "Lỗi mua quá tồn kho", JOptionPane.ERROR_MESSAGE);
					throw new ArithmeticException("Số lượng tồn kho của bạn không đủ");
				}
				
				// TODO Auto-generated method stub
				//Sau khi kiểm tra thấy được thì sẽ bắt đầu set thuộc tính cho chi tiết hoá đơn và đóng frame hiện tại lại
				chiTietHoaDon.setMa_mat_hang(Integer.parseInt((textFieldMaMatHang.getText().substring(1))));
				chiTietHoaDon.setDon_gia(Integer.parseInt(textFieldDonGia.getText()));
				chiTietHoaDon.setSo_luong(Integer.parseInt(textFieldSoLuong.getText()));
				chiTietHoaDon.setTen_mat_hang(comboBox.getSelectedItem().toString());
				chiTietHoaDon.setThanh_tien(Integer.parseInt(textFieldThanhTien.getText()));
//				System.out.println(chiTietHoaDon.getMa_mat_hang()+"\t"+chiTietHoaDon.getMa_mat_hang()+"\t"+chiTietHoaDon.getSo_luong()+"\t"+chiTietHoaDon.getTen_mat_hang()+"\t"+chiTietHoaDon.getThanh_tien());
				frame.dispose();				
			}
		});
	}
}
