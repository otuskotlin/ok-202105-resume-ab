package ru.otus.otuskotlin.resume.backend.common.exceptions

class ResumeStubNotFound (stubCase: String): Throwable("There is no matchable worker to handle case: $stubCase")