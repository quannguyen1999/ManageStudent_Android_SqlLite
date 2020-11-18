package com.example.managestudent_android_sqllite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.managestudent_android_sqllite.adapters.ListProductAdapter;
import com.example.managestudent_android_sqllite.daos.DBManager;
import com.example.managestudent_android_sqllite.models.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickItemListener {
    private RecyclerView rclView;

    private ListProductAdapter listProductAdapter;

    private ArrayList<Product> listProduct;

    private int itemSelcted = -1;

    Button btnThem, btnXoaRong, btnUpdate, btnXoa;

    EditText edtMa, edtTen, edtSoLuong;

    //daos
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        metaData();

        initData();

        listentRequestFromListener();

        //hide app bar
        getSupportActionBar().hide();

//        initSpinner();
    }

    private void listentRequestFromListener() {
        btnXoaRong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearData();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemSelcted == -1 ) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Chưa chọn hàng ");
                    alert.setMessage("VUi lòng chọn :(");
                    alert.setNegativeButton("Ok", null);
                    alert.show();
                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Delete?")
                            .setMessage("Are you sure ? ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String ma = edtMa.getText().toString();
                                    if(ma.isEmpty()){
                                        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                        alert.setTitle("Lỗi input ");
                                        alert.setMessage("vui lòng  chọn bảng cần xóa:(");
                                        alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                edtMa.requestFocus();
                                            }
                                        });
                                        alert.show();
                                    }
                                    Product product = dbManager.findById(ma);
                                    if(product == null){
                                        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                        alert.setTitle("Lỗi ");
                                        alert.setMessage("không tìm thấy mã");
                                        alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                edtMa.requestFocus();
                                            }
                                        });
                                        alert.show();
                                    }
                                    dbManager.deleteProduct(product);
                                    listProduct.remove(itemSelcted);
                                    listProductAdapter.notifyDataSetChanged();
                                    clearData();
                                    itemSelcted = -1;
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }

            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = edtMa.getText().toString();
                String ten = edtTen.getText().toString();
                String soLuong = edtSoLuong.getText().toString();
                if (ma.isEmpty()) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Lỗi input ");
                    alert.setMessage("VUi lòng nhập mã :(");
                    alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtMa.requestFocus();
                        }
                    });
                    alert.show();
                }

                if (ten.isEmpty()) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Lỗi input ");
                    alert.setMessage("VUi lòng nhập tên :(");
                    alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtTen.requestFocus();
                        }
                    });
                    alert.show();
                }

                if (soLuong.isEmpty()) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Lỗi input ");
                    alert.setMessage("VUi lòng nhập số lượng :(");
                    alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtSoLuong.requestFocus();
                        }
                    });
                    alert.show();
                }

                Product product = null;

                try {
                    product = new Product(ma, ten, Integer.parseInt(soLuong));
                } catch (Exception e) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Lỗi input ");
                    alert.setMessage("số lượng không hợp lệ:(");
                    alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtSoLuong.requestFocus();
                        }
                    });
                    alert.show();
                }

                dbManager.addProduct(product);
                listProduct.add(product);
                listProductAdapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();

                clearData();

                closeKeyBoard();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = null;
                String ma = edtMa.getText().toString();
                String ten = edtTen.getText().toString();
                String soLuong = edtSoLuong.getText().toString();
                if (ma.isEmpty()) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Lỗi input ");
                    alert.setMessage("VUi lòng nhập mã để cập nhập:(");
                    alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtMa.requestFocus();
                        }
                    });
                    alert.show();
                }

                if (ten.isEmpty()) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Lỗi input ");
                    alert.setMessage("VUi lòng nhập tên để cập nhập:(");
                    alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtTen.requestFocus();
                        }
                    });
                    alert.show();
                }

                if (soLuong.isEmpty()) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Lỗi input ");
                    alert.setMessage("VUi lòng nhập số lượng để cập nhập:(");
                    alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtSoLuong.requestFocus();
                        }
                    });
                    alert.show();
                }

                try {
                    Integer.parseInt(soLuong);
                } catch (Exception e) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Lỗi input ");
                    alert.setMessage("số lượng không hợp lệ:(");
                    alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtSoLuong.requestFocus();
                        }
                    });
                    alert.show();
                }

                product = dbManager.findById(ma);
                if(product == null){
                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Lỗi ");
                    alert.setMessage("không tìm thấy mã");
                    alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtMa.requestFocus();
                        }
                    });
                    alert.show();
                }
                product.setSoLuong(Integer.parseInt(soLuong));
                product.setNameProduct(ten);

                dbManager.updateProduct(product);
                listProduct.get(itemSelcted).setNameProduct(ten);
                listProduct.get(itemSelcted).setSoLuong(Integer.parseInt(soLuong));
                listProductAdapter.notifyItemChanged(itemSelcted);

                Toast.makeText(MainActivity.this, "cập nhập thành công", Toast.LENGTH_LONG).show();

                clearData();

                closeKeyBoard();




            }
        });


    }

    private void clearData() {
        edtMa.setText("");
        edtSoLuong.setText("");
        edtTen.setText("");
    }

    private void initData() {

        dbManager = new DBManager(this);
            listProduct = new ArrayList<>();

//            listProduct.add(new Product("sp101"))

//        dbManager.addProduct(new Product("sp01", "bit com suon", 10));
//        dbManager.addProduct(new Product("sp02", "ba con soi", 10));
//        dbManager.addProduct(new Product("sp03", "khong gai", 10));
//        dbManager.addProduct(new Product("sp04", "24h", 10));
//        dbManager.addProduct(new Product("sp05", "bao cao su", 10));

       listProduct = (ArrayList<Product>) dbManager.getAllProduct();



        listProductAdapter = new ListProductAdapter(this, listProduct, this);

        rclView.setAdapter(listProductAdapter);

        rclView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void metaData() {
        edtMa = findViewById(R.id.edtMa);
        edtTen = findViewById(R.id.edtTen);
        edtSoLuong = findViewById(R.id.edtSoLuong);
        btnThem = findViewById(R.id.btnThem);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnXoa = findViewById(R.id.btnXoa);
        btnXoaRong = findViewById(R.id.btnXoaRong);
        rclView = findViewById(R.id.rclView);
    }

    private void closeKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onItemCLick(Product listProduct, int position) {
        edtMa.setText(listProduct.getMa());
        edtSoLuong.setText(String.valueOf(listProduct.getSoLuong()));
        edtTen.setText(listProduct.getNameProduct());
        itemSelcted = position;
    }
}