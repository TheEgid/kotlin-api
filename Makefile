# include ./my_server/.env
export

COMPOSE_BAKE=true

LANG=ru_RU.UTF-8

all: run clean run


run:
	@docker compose build
	@docker compose up -d
	@docker ps


runner:
	@docker compose up


stop:
	@docker compose down


clean:
	@sleep 1
	@docker system prune -f
	@docker system prune -f --volumes


totalclean:
	@sleep 1
	@docker compose down
	@docker image prune -f
	@docker builder prune -f


# test:
# 	@echo "jest tests will start after 3 seconds..."
# 	@sleep 3
# 	@docker exec -it my_server_container test


up:
	@sudo dnf --refresh update && sudo dnf upgrade
