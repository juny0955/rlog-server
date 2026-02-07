package junyoung.dev.rlogserver.global.pagination;

public record PageRequestParam(int page, int size) {

	public PageRequestParam {
		if (page < 0) {
			page = 0;
		}
		if (size <= 0) {
			size = 20;
		}
		if (size > 100) {
			size = 100;
		}
	}

	public static PageRequestParam of(Integer page, Integer size) {
		return new PageRequestParam(
			page != null ? page : 0,
			size != null ? size : 20
		);
	}

	public long offset() {
		return (long) page * size;
	}
}
