package com.ndmawpu.tinin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText edtChiSoMoi, edtChiSoCu, edtSoHo;
    TextView txtKetQua, txtkWh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
    }

    private void addControls() {
        edtChiSoCu = findViewById(R.id.edtChiSoCu);
        edtChiSoMoi = findViewById(R.id.edtChiSoMoi);
        edtSoHo = findViewById(R.id.edtSoHo);
        txtkWh = findViewById(R.id.txtkWh);
        txtKetQua = findViewById(R.id.txtKetqua);

        // tự động cập nhật số kWh
        edtChiSoCu.addTextChangedListener(textWatcher);
        edtChiSoMoi.addTextChangedListener(textWatcher);
    }

    public String tinhGiaSinhHoatBacThang(double A, double B, double C)
    {   // Tính giá sinh hoạt bậc thang
        double kq;
        if ((B-A) <= 50 * C)
        {
            kq = ((B-A)*1484);
        } else if ((B-A) <= 100 * C) {
            kq = (50*C * 1484) + ((B-A) - 50*C) * 1533;
        } else if ((B-A) <= 200 * C) {
            kq = (50*C*1484) + (50*C*1533) + ((B-A) - 100*C) * 1786;
        } else if ((B - A <= 300 * C)) {
            kq = (50*C*1484) + (50*C*1533) + (100*C*1786) + ((B-A) - 200*C) * 2242;
        } else if ((B - A) <= 400 * C) {
            kq = (50*C*1484) + (50*C*1533) + (100*C*1786) + (100*C*2242) + ((B-A) - 300*C) * 2503;
        } else
        {
            kq = (50*C*1484) + (50*C*1533) + (100*C*1786) + (100*C*2242) + (100*C*2503) + ((B-A) - 400*C) * 2587;
        }
        return "Tổng số tiền điện giá sinh hoạt (" + C + " hộ): " + "\n" + String.format(Locale.US, "%,.0f" ,kq);
    }

    public String tinhGiaKinhDoanh(double A, double B)
    {
        // Tính giá Kinh doanh
        double kq = (B-A)*2320;
        return "Tổng số tiền điện giá kinh doanh: " + "\n" +  String.format(Locale.US, "%,.0f" ,kq);
    }

    public String tinhGiaSanXuat(double A, double B)
    {
        // Tính giá sản xuất
        double kq = (B-A)*1518;
        return "Tổng số tiền điện giá sản xuất: " + "\n" +  String.format(Locale.US, "%,.0f" ,kq);
    }

    // tự động cập nhật số kWh
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!edtChiSoCu.getText().toString().equals("") && !edtChiSoMoi.getText().toString().equals(""))
            {
                int num1 = Integer.parseInt(edtChiSoCu.getText().toString());
                int num2 = Integer.parseInt(edtChiSoMoi.getText().toString());

                // update TextView
                txtkWh.setText( String.format("Số kWh dùng: %s", (num2 - num1)));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void xulyThoat(View view) {
        // Xử lý nút thoát
        finish();
    }

    public void xulyXoa(View view) {
        // Xử lý nút xóa
        edtChiSoMoi.getText().clear();
        edtChiSoCu.getText().clear();
        edtSoHo.getText().clear();
        txtkWh.setText("Số kWh dùng: ");
        // di chuyển đến ô chỉ số cũ
        edtChiSoCu.requestFocus();
    }

    public void xulySHBT(View view) {
        try
        {
            double cu = Double.parseDouble(edtChiSoCu.getText().toString());
            double moi = Double.parseDouble(edtChiSoMoi.getText().toString());
            double soho = Double.parseDouble(edtSoHo.getText().toString());
            if (cu > moi)
            {
                txtKetQua.setText("Chỉ số mới phải lớn hơn chỉ số cũ, Vui lòng nhập lại");
                Toast.makeText(MainActivity.this, "Chỉ số mới phải lớn hơn chỉ số cũ, Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                edtChiSoMoi.getText().clear();
                edtChiSoCu.getText().clear();
                edtChiSoCu.requestFocus();
            }
            else
            {
                String kq = tinhGiaSinhHoatBacThang(cu, moi, soho);
                txtKetQua.setText(kq);
            }
        }
        catch (Exception e)
        {
            txtKetQua.setText("Lỗi, Vui lòng nhập lại");
            Toast.makeText(MainActivity.this, "Lỗi, vui lòng nhập lại", Toast.LENGTH_SHORT).show();
        }
    }

    public void xulyKD(View view) {
        try
        {
            double cu = Double.parseDouble(edtChiSoCu.getText().toString());
            double moi = Double.parseDouble(edtChiSoMoi.getText().toString());
            if (cu > moi)
            {
                txtKetQua.setText("Chỉ số mới phải lớn hơn chỉ số cũ, Vui lòng nhập lại");
                Toast.makeText(MainActivity.this, "Chỉ số mới phải lớn hơn chỉ số cũ, Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                edtChiSoMoi.getText().clear();
                edtChiSoCu.getText().clear();
                edtChiSoCu.requestFocus();
            }
            else
            {
                String kq = tinhGiaKinhDoanh(cu, moi);
                txtKetQua.setText(kq);
            }
        }
        catch (Exception e)
        {
            txtKetQua.setText("Lỗi, Vui lòng nhập lại");
            Toast.makeText(MainActivity.this, "Lỗi, vui lòng nhập lại", Toast.LENGTH_SHORT).show();
        }
    }

    public void xulySX(View view) {
        try
        {
            double cu = Double.parseDouble(edtChiSoCu.getText().toString());
            double moi = Double.parseDouble(edtChiSoMoi.getText().toString());
            if (cu > moi)
            {
                txtKetQua.setText("Chỉ số mới phải lớn hơn chỉ số cũ, Vui lòng nhập lại");
                Toast.makeText(MainActivity.this, "Chỉ số mới phải lớn hơn chỉ số cũ, Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                edtChiSoMoi.getText().clear();
                edtChiSoCu.getText().clear();
                edtChiSoCu.requestFocus();
            }
            else
            {
                String kq = tinhGiaSanXuat(cu, moi);
                txtKetQua.setText(kq);
            }
        }
        catch (Exception e)
        {
            txtKetQua.setText("Lỗi, Vui lòng nhập lại");
            Toast.makeText(MainActivity.this, "Lỗi, vui lòng nhập lại", Toast.LENGTH_SHORT).show();
        }
    }
}