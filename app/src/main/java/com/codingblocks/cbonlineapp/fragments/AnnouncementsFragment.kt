package com.codingblocks.cbonlineapp.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingblocks.cbonlineapp.R
import com.codingblocks.cbonlineapp.adapters.InstructorDataAdapter
import com.codingblocks.cbonlineapp.adapters.SectionDetailsAdapter
import com.codingblocks.cbonlineapp.database.AppDatabase
import com.codingblocks.cbonlineapp.database.CourseSection
import com.codingblocks.cbonlineapp.database.Instructor
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.fragment_annoucements.view.*
import kotlinx.android.synthetic.main.fragment_course_content.view.*

private const val ARG__ATTEMPT_ID = "attempt_id"

class AnnouncementsFragment : Fragment() {
    private lateinit var database: AppDatabase
    lateinit var attemptId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            attemptId = it.getString(ARG__ATTEMPT_ID)!!
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_annoucements, container, false)
        database = AppDatabase.getInstance(context!!)
        val instructorDao = database.instructorDao()
        val instructorList = ArrayList<Instructor>()
        val instructorAdapter = InstructorDataAdapter(instructorList)
        view.instructorRv.layoutManager = LinearLayoutManager(context)
        view.instructorRv.adapter = instructorAdapter

        instructorDao.getInstructors(attemptId).observe(this, Observer<List<Instructor>> {
            instructorAdapter.setData(it as ArrayList<Instructor>)
        })

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
                AnnouncementsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG__ATTEMPT_ID, param1)
                    }
                }
    }


}
