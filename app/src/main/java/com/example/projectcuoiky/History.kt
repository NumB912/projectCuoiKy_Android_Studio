package com.example.projectcuoiky

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.time.LocalDate

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [History.newInstance] factory method to
 * create an instance of this fragment.
 */
class History : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutHistory = inflater.inflate(R.layout.fragment_history, container, false)

        val barChart = layoutHistory.findViewById<BarChart>(R.id.barChart)

        // Dữ liệu doanh thu cho mỗi ngày trong tuần
        val entries = listOf(
            BarEntry(0f, 100f), // Thứ 2
            BarEntry(1f, 20f), // Thứ 3
            BarEntry(2f, 18f), // Thứ 4
            BarEntry(3f, 25f), // Thứ 5
            BarEntry(4f, 10f), // Thứ 6
            BarEntry(5f, 30f), // Thứ 7
            BarEntry(6f, 22f)  // Chủ Nhật
        )

        // Tên các ngày trong tuần
        val daysOfWeek = listOf("Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "CN")

        // Tạo DataSet và tùy chỉnh
        val barDataSet = BarDataSet(entries,"Task").apply {
            color = ContextCompat.getColor(requireContext(), R.color.Bocchi) // Màu cột
            valueTextColor = Color.BLACK // Màu chữ hiển thị giá trị
            valueTextSize = 13f // Kích thước chữ giá trị
        }

        // Kết hợp DataSet thành BarData
        val barData = BarData(barDataSet)
        barData.barWidth = 0.9f // Độ rộng cột

        // Gắn dữ liệu vào biểu đồ
        barChart.data = barData
        barChart.center

        // Tùy chỉnh trục X để hiển thị tên ngày
        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(daysOfWeek) // Hiển thị tên ngày trên trục X
        xAxis.granularity = 1f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.BLACK

        // Tùy chỉnh trục Y
        barChart.axisLeft.textColor = Color.BLACK // Màu chữ trục Y bên trái
        barChart.axisRight.isEnabled = false // Ẩn trục Y bên phải

        // Tùy chỉnh biểu đồ
        barChart.description.isEnabled = false // Ẩn mô tả
        barChart.setFitBars(true) // Căn chỉnh các cột vừa khung
        barChart.animateY(1000) // Thêm hiệu ứng hoạt hình
        // Làm mới biểu đồ
        barChart.invalidate()

        return layoutHistory
    }


    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).checkFragment(this)
    }
}