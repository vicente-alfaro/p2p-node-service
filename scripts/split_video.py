from pathlib import Path
import math
import shutil

BASE_DIR = Path(__file__).resolve().parent.parent

INPUT_VIDEO = BASE_DIR / "sample-video" / "sample.mp4"

NODE_A_FRAGMENTS = BASE_DIR / "data" / "node-a" / "fragments"
NODE_B_FRAGMENTS = BASE_DIR / "data" / "node-b" / "fragments"
NODE_C_FRAGMENTS = BASE_DIR / "data" / "node-c" / "fragments"

TOTAL_FRAGMENTS = 10


def clean_folder(folder: Path):
    folder.mkdir(parents=True, exist_ok=True)

    for item in folder.iterdir():
        if item.is_file() and item.name.endswith(".part"):
            item.unlink()


def split_video():
    if not INPUT_VIDEO.exists():
        raise FileNotFoundError(f"No se encontró el video: {INPUT_VIDEO}")

    clean_folder(NODE_A_FRAGMENTS)
    clean_folder(NODE_B_FRAGMENTS)
    clean_folder(NODE_C_FRAGMENTS)

    video_bytes = INPUT_VIDEO.read_bytes()
    total_size = len(video_bytes)
    fragment_size = math.ceil(total_size / TOTAL_FRAGMENTS)

    print(f"Video original: {INPUT_VIDEO}")
    print(f"Tamaño total: {total_size} bytes")
    print(f"Tamaño aproximado por fragmento: {fragment_size} bytes")
    print()

    generated_fragments = []

    for index in range(TOTAL_FRAGMENTS):
        start = index * fragment_size
        end = min(start + fragment_size, total_size)

        fragment_bytes = video_bytes[start:end]
        fragment_name = f"fragment-{index + 1:03d}.part"

        temp_fragment_path = BASE_DIR / "data" / fragment_name
        temp_fragment_path.write_bytes(fragment_bytes)

        generated_fragments.append(temp_fragment_path)

    distribute_fragments(generated_fragments)

    print()
    print("Fragmentación terminada.")
    print("Distribución inicial:")
    print("node-a:", list_files(NODE_A_FRAGMENTS))
    print("node-b:", list_files(NODE_B_FRAGMENTS))
    print("node-c:", list_files(NODE_C_FRAGMENTS))


def distribute_fragments(fragment_paths):
    for fragment_path in fragment_paths:
        name = fragment_path.name

        if name in ["fragment-001.part", "fragment-002.part", "fragment-003.part"]:
            destination = NODE_A_FRAGMENTS / name

        elif name in ["fragment-004.part", "fragment-005.part", "fragment-006.part"]:
            destination = NODE_B_FRAGMENTS / name

        else:
            destination = NODE_C_FRAGMENTS / name

        shutil.move(str(fragment_path), str(destination))


def list_files(folder: Path):
    return sorted([item.name for item in folder.iterdir() if item.is_file()])


if __name__ == "__main__":
    split_video()