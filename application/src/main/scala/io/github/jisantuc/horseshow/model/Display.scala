package io.github.jisantuc.horseshow.model

enum Display:
  case On, Off

  def toggle: Display = this match {
    case On  => Off
    case Off => On
  }
