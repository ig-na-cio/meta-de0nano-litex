# meta-de0nano-litex

Layer para Yocto para soporte de la de0nano con SoC creado con `linux-on-litex-vexriscv`, con patches, device tree y configuraciones obtenidas de dicho proyecto. Sigue la estructura tradicional de las layers de The Yocto Project.

## Archivos
- `conf/machine/de0nano-litex.conf`: Definición de parámetros de la arquitectura, bootloader, kernel, filesystem y UART.

- `conf/layer.conf`: Parámetros de la layer sobre archivos, recipes, branch (scarthgap en nuestro caso) y prioridad con respecto a las otras layers.

- `recipes-bsp/`: Recipes appends que fuerzan que no se genere u-boot y opensbi.

- `recipes-core/images/litex-minimal-image.bb`: Recipe para toda la imagen. Configura formato de la imagen del kernel, filesystem, paquetes a instalar, etc.

- `recipes-kernel/linux/litex-litex_6.9.bb`: Recipe para el kernel. Se obtiene el kernel oficial 6.9 de git, se elige formato, se fuerzan las flags de  compilación y se copia el device tree al arbol del kernel.

- `recipes-core/linux/files/`: Archivos generados por Litex. Patches con drivers para Linux 6.9, defconfig y el device tree.

- `recipes-example/led-demo/`: Archivos para imprimir un Hola Mundo y un patrón de leds en la plataforma y la receta para compilarlos.

> NOTA: Se requiere de `meta-riscv`, y de aquellas que vienen con poky por defecto, para utilizar esta layer.

## Uso

1. Crear el build. Para mas información, referirse al manual de Yocto

``` bash
# Dentro de poky/
$ source oe-init-build-env build-de0nano-litex/
```

2. Agregar la layer al build con cómando o manualmente

``` bash
# Dentro de poky/
$ bitbake-layers add-layer meta-de0nano-litex
```

3. En local.conf cambiar la máquina
``` bash
MACHINE = "de0nano-litex"
```

4. Agregar al final estas configuraciones:

```
# Sin nada extra
EXTRA_IMAGEDEPENDS = ""

# Sacamos algunas cosas de la distribución
DISTRO_FEATURES:remove = "bluetooth wifi nfs zeroconf pci 3g nfc x11 opengl vulkan wayland"

# Eliminar Python y Perl de las dependencias
HOSTTOOLS_NONFATAL += "python3 perl"

PACKAGE_CLASSES = "package_ipk"

DISTRO_FEATURES = "largefile"

# Evitar que se instalen cosas de manera automática
BAD_RECOMMENDATIONS = "*"

# Sin locales
IMAGE_LINGUAS = ""
```

5. Para construir:

- Imagen completa

``` Bash
bitbake litex-minimal-image
```

- Sólo kernel

``` Bash
bitbake litex-linux
```
