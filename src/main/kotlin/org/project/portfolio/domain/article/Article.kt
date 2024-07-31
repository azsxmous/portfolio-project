package org.project.portfolio.domain.article

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.util.Date

@Entity(name = "article")
// 제목, 내용, 작성자, 생성시간, 수정시간
class Article (
    @Column(length = 200)
    var title: String,
    @Column(length = 1000)
    var content: String,
    var userId:Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var createdAt: Date?=null
    var updatedAt: Date?=null

    @PrePersist
    fun prePersist() {
        createdAt = Date()
        updatedAt = Date()
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Date()
    }

}