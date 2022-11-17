# base recipe: meta/recipes-devtools/python/python3-pygobject_3.32.1.bb
# base branch: master
# base commit: e8cca73c92ebf046d103dec0811afeff157197e0

SUMMARY = "Python GObject bindings"
SECTION = "devel/python"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=a916467b91076e631dd8edb7424769c7"

GNOMEBASEBUILDCLASS = "meson"
inherit gnomebase distutils3-base gobject-introspection upstream-version-is-even

inherit debian-package
require recipes-debian/sources/pygobject.inc

DEPENDS += "python3 glib-2.0"

SRCNAME="pygobject"
SRC_URI += " \
    file://0001-Rebase-Do-not-build-tests.patch \
    file://pygobject-use-specific-py3cairo-version.patch \
"

UNKNOWN_CONFIGURE_WHITELIST = "introspection"

DEBIAN_UNPACK_DIR = "${WORKDIR}/${SRCNAME}-${PV}"
DEBIAN_QUILT_PATCHES = ""

PACKAGECONFIG ??= "${@bb.utils.contains_any('DISTRO_FEATURES', [ 'directfb', 'wayland', 'x11' ], 'cairo', '', d)}"

RDEPENDS_${PN} += "python3-pkgutil"

# python3-pycairo is checked on configuration -> DEPENDS
# we don't link against python3-pycairo -> RDEPENDS
PACKAGECONFIG[cairo] = "-Dpycairo=true,-Dpycairo=false, cairo python3-pycairo, python3-pycairo"

BBCLASSEXTEND = "native"
PACKAGECONFIG_class-native = ""

do_install_append_class-target() {
  # Temporary workaround to fix following duplicate usr directory error.
  # ERROR: python3-pygobject-3.30.4-r0 do_package: QA Issue: python3-pygobject: Files/directories were installed but not shipped in any package:
  #  /usr/usr/lib/python3.7/site-packages/PyGObject-3.30.4.egg-info
  #  /usr/usr/lib/python3.7/site-packages/pycairo-1.18.2.egg-info
  #  /usr/usr/lib/python3.7/site-packages/pygtkcompat
  #  /usr/usr/lib/python3.7/site-packages/pygtkcompat/pygtkcompat.py
  #  /usr/usr/lib/python3.7/site-packages/pygtkcompat/generictreemodel.py
  if [ -d ${D}/usr/usr/lib ]; then
    mv ${D}/usr/usr/lib/* ${D}/usr/lib/.
    rm -fr ${D}/usr/usr
  fi
}
