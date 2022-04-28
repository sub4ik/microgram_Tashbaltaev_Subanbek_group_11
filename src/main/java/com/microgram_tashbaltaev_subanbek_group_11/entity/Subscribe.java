package com.microgram_tashbaltaev_subanbek_group_11.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "subscribe")
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor


public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private User subscriber;    //кто подписался

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private User subscription;    //на кого подписался

    @Column(name = "date_added")
    private LocalDateTime dateAdded;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscribe)) return false;
        Subscribe subscribe = (Subscribe) o;
        return getId().equals(subscribe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
