# base recipe: meta-openembedded/meta-oe/recipes-extended/jansson/jansson_2.12.bb
# base branch: warrior
# base commit: 3d4e72ce6d8be437708096c2557c47c6186309dc

SUMMARY = "Jansson is a C library for encoding, decoding and manipulating JSON data"
HOMEPAGE = "http://www.digip.org/jansson/"
BUGTRACKER = "https://github.com/akheron/jansson/issues"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fc2548c0eb83800f29330040e18b5a05"

inherit debian-package
require recipes-debian/sources/jansson.inc
DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_QUILT_PATCHES = ""

inherit autotools pkgconfig

