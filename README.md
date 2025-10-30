# meta-de0nano-litex

1. Crear el build

2. Agregar la layer al build

3. En local.conf cambiar la maquina a:
MACHINE = "de0nano-litex"

4. Agregar al final estas configuraciones:

# Sin nada extra
EXTRA_IMAGEDEPENDS = ""

# Sacamos algunas cosas de la distribucion
DISTRO_FEATURES:remove = "bluetooth wifi nfs zeroconf pci 3g nfc x11 opengl vulkan wayland"

# Eliminar Python y Perl de las dependencias
HOSTTOOLS_NONFATAL += "python3 perl"

PACKAGE_CLASSES = "package_ipk"

DISTRO_FEATURES = "largefile"

# Evitar que se instalen cosas de manera automatica
BAD_RECOMMENDATIONS = "*"

# Sin locales
IMAGE_LINGUAS = ""

5. Para construir:

- Todo
bitbake litex-minimal-image

- Solo kernel
bitbake litex-linux

