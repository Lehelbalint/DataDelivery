import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.datadelivery.R
import com.example.datadelivery.ViewModels.SharedChartsViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class DistributionHistogram : DialogFragment() {

    private lateinit var barChart: BarChart
    val sharedChartsViewModel : SharedChartsViewModel by activityViewModels ()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_charts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        barChart = view.findViewById(R.id.bar_chart)
        setupChart()
    }

    private fun setupChart() {
        val entries = mutableListOf<BarEntry>()
        val gradeCounts = calculateGradeCounts()

        for (i in gradeCounts.indices) {
            val gradeCount = gradeCounts[i]
            entries.add(BarEntry(i.toFloat(), gradeCount.toFloat()))
        }

        val dataset = BarDataSet(entries, "Grade Distribution")
        dataset.color = setColor("#06d48f")

        val data = BarData(dataset)
        data.barWidth = 0.8f

        barChart.data = data
        barChart.description.isEnabled = false
        barChart.setDrawGridBackground(false)
        barChart.animateY(1000)
    }
    private fun setColor (color: String): Int {
        val hexColor = "#06d48f"

        val red = Integer.parseInt(hexColor.substring(1, 3), 16)
        val green = Integer.parseInt(hexColor.substring(3, 5), 16)
        val blue = Integer.parseInt(hexColor.substring(5, 7), 16)

        return Color.rgb(red, green, blue)

    }
    private fun calculateGradeCounts(): List<Int> {
        val gradeCounts = IntArray(11) // Az 1-től 5-ig tartó jegyek számának rögzítéséhez inicializálunk egy IntArray-t

        // Az összes jegyet bejárjuk és megszámoljuk a jegyeket
        for (grade in sharedChartsViewModel.GradesHistogram) {
            if (grade.attributes.grade in 1..10) { // Ellenőrizzük, hogy a jegy a megfelelő tartományban van
                gradeCounts[grade.attributes.grade]++
            }
        }

        return gradeCounts.toList() // Az IntArray-t átalakítjuk List<Int>-é
    }
}