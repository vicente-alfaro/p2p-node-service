from pathlib import Path
import hashlib

BASE_DIR = Path(__file__).resolve().parent.parent

NODE_A_FRAGMENTS = BASE_DIR / "data" / "node-a" / "fragments"
NODE_B_FRAGMENTS = BASE_DIR / "data" / "node-b" / "fragments"
NODE_C_FRAGMENTS = BASE_DIR / "data" / "node-c" / "fragments"

OUTPUT_DIR = BASE_DIR / "data" / "reconstructed"
OUTPUT_VIDEO = OUTPUT_DIR / "reconstructed.mp4"

ORIGINAL_VIDEO = BASE_DIR / "sample-video" / "sample.mp4"

TOTAL_FRAGMENTS = 10


def find_fragment(fragment_name: str):
    possible_locations = [
        NODE_A_FRAGMENTS / fragment_name,
        NODE_B_FRAGMENTS / fragment_name,
        NODE_C_FRAGMENTS / fragment_name,
    ]

    for path in possible_locations:
        if path.exists() and path.is_file():
            return path

    return None


def calculate_sha256(file_path: Path):
    sha256 = hashlib.sha256()

    with file_path.open("rb") as file:
        while True:
            chunk = file.read(1024 * 1024)

            if not chunk:
                break

            sha256.update(chunk)

    return sha256.hexdigest()


def rebuild_video():
    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

    missing_fragments = []

    with OUTPUT_VIDEO.open("wb") as output_file:
        for index in range(1, TOTAL_FRAGMENTS + 1):
            fragment_name = f"fragment-{index:03d}.part"
            fragment_path = find_fragment(fragment_name)

            if fragment_path is None:
                missing_fragments.append(fragment_name)
                continue

            print(f"Agregando {fragment_name} desde {fragment_path}")
            output_file.write(fragment_path.read_bytes())

    if missing_fragments:
        print()
        print("No se pudo reconstruir correctamente el video.")
        print("Fragmentos faltantes:")
        for fragment in missing_fragments:
            print(f"- {fragment}")

        if OUTPUT_VIDEO.exists():
            OUTPUT_VIDEO.unlink()

        return

    print()
    print("Video reconstruido correctamente.")
    print(f"Archivo generado: {OUTPUT_VIDEO}")

    if ORIGINAL_VIDEO.exists():
        original_hash = calculate_sha256(ORIGINAL_VIDEO)
        reconstructed_hash = calculate_sha256(OUTPUT_VIDEO)

        print()
        print("Validación SHA-256:")
        print(f"Original:      {original_hash}")
        print(f"Reconstruido: {reconstructed_hash}")

        if original_hash == reconstructed_hash:
            print("Resultado: el video reconstruido es idéntico al original.")
        else:
            print("Resultado: el video reconstruido NO coincide con el original.")
    else:
        print("No se encontró el video original para comparar hash.")


if __name__ == "__main__":
    rebuild_video()