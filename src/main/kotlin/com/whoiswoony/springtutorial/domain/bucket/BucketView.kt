package com.whoiswoony.springtutorial.domain.bucket

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.Date
import javax.persistence.*

@Entity(name="BUCKET_VIEW")
@Table(uniqueConstraints = [UniqueConstraint(columnNames =  ["BUCKET_ID", "ipAddress", "createdDate"])])
class BucketView (
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "BUCKET_ID")
    val bucket:Bucket,

    val ipAddress:String,

    @Temporal(TemporalType.DATE)
    var createdDate:Date = Date(),

    @Temporal(TemporalType.TIME)
    var createdTime:Date = Date(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null
){
    @PrePersist
    private fun setCreatedDate(){
        createdDate = Date()
        createdTime = Date()
    }
}