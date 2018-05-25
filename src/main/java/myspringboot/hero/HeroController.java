package myspringboot.hero;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/heros")
public class HeroController {

	private List<Hero> heros = new ArrayList<>();	
	
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());

	HeroController() {
		buildHeros();
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Hero> getHeros() {
		return this.heros;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Hero getHero(@PathVariable("id") Long id) {
		return this.heros.stream().filter(hero -> hero.getId() == id).findFirst().orElse(null);
	}

	/**
	 * 추가 
	 * @param Hero
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Hero saveHero(@RequestBody Hero hero) {
		Long nextId = 0L;
		if (this.heros.size() != 0) {
			//마지막까지 skip > findFirst(= 마지막 hero) > +1 새로 부여할 아이디 > 추가하여 저장
			Hero lastHero = this.heros.stream().skip(this.heros.size() - 1).findFirst().orElse(null);
			nextId = lastHero.getId() + 1;
		}

		hero.setId(nextId);
		this.heros.add(hero);
		return hero;

	}

	/**
	 * 수정
	 * @param hero
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Hero updateHero(@RequestBody Hero hero) {		
		Hero modifiedHero = this.heros.stream().filter(u -> u.getId() == hero.getId()).findFirst().orElse(null);
		modifiedHero.setName(hero.getName());
		return modifiedHero;
	}

	/**
	 * 삭제
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean deleteHero(@PathVariable Long id) {
		Hero deleteHero = this.heros.stream().filter(hero -> hero.getId() == id).findFirst().orElse(null);
		if (deleteHero != null) {
			this.heros.remove(deleteHero);
			return true;
		} else  {
			return false;
		}
	}

	/**
	 * 검색
	 * @param name
	 * @return
	 */
	@RequestMapping(value ="/name/{name}", method = RequestMethod.GET)
	public List<Hero> searchHeroes(@PathVariable String name){
		//java 1.8 이상 사용 가능한 코드
		return this.heros.stream().filter(hero -> hero.getName().contains(name)).collect(Collectors.toList());
	}
	
	
	void buildHeros() {
		Hero hero1 = new Hero(11L,"김하나");
		Hero hero2 = new Hero(12L,"김두이");
		Hero hero3 = new Hero(13L,"김서이");
		Hero hero4 = new Hero(14L,"김너이");
		Hero hero5 = new Hero(15L,"김다섯");
		Hero hero6 = new Hero(16L,"김여섯");
		Hero hero7 = new Hero(17L,"김일곱");
		Hero hero8 = new Hero(18L,"김여덟");
		Hero hero9 = new Hero(19L,"김아홉");
		Hero hero10 = new Hero(20L,"김여얼");

		heros.add(hero1);
		heros.add(hero2);
		heros.add(hero3);
		heros.add(hero4);
		heros.add(hero5);
		heros.add(hero6);
		heros.add(hero7);
		heros.add(hero8);
		heros.add(hero9);
		heros.add(hero10);
		
	}
}