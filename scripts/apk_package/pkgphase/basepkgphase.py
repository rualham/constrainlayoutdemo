#!/usr/bin/env python
# -*- coding: utf-8 -*-
from abc import abstractmethod


class BasePackagePhase(object):
    depend = None

    def __init__(self, build_input):
        self._build_input = build_input
        self._build_output = None

    @abstractmethod
    def build(self):
        pass

    @abstractmethod
    def self_space(self):
        pass

    @property
    def build_input(self):
        return self._build_input

    # @build_input.setter
    # def build_input(self, value):
    #     self._build_input = value

    @property
    def build_output(self):
        return self._build_output

    @build_output.setter
    def build_output(self, value):
        self._build_output = value
