package io.github.jisantuc.model

enum Display:
  case On, Off

  def toggle: Display = this match {
    case On => Off
    case Off => On
  }
